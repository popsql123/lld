package com.example.parkingLot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    static class User {
        String userId;
    }

    enum RateLimiterType {
        SLIDING_WINDOW_LOG
    }

    static abstract class RateLimiterStrategy {

        RateLimiterType type;
        RateLimiterConfig config;

        public RateLimiterStrategy(RateLimiterType type, RateLimiterConfig config) {
            this.type = type;
            this.config = config;
        }

        abstract boolean allow(String userId, long reqTimestamp);
    }

    static class RateLimiterConfig {

        int limit;
        long windowMs;

        public RateLimiterConfig(int limit, long window) {
            this.limit = limit;
            this.windowMs = window;
        }
    }

    static class SlidingWindowLog extends RateLimiterStrategy {

        private final ConcurrentHashMap<String, Deque<Long>> map =
                new ConcurrentHashMap<>();

        SlidingWindowLog(RateLimiterConfig config) {
            super(RateLimiterType.SLIDING_WINDOW_LOG, config);
        }

        @Override
        public boolean allow(String userId, long reqTimestamp) {
            long now = reqTimestamp*1000;

            Deque<Long> q =
                    map.computeIfAbsent(userId, k -> new ArrayDeque<>());

            synchronized (q) {

                while (!q.isEmpty()
                        && (now - q.peekFirst()) >= config.windowMs) {
                    q.pollFirst();
                }

                if (q.size() < config.limit) {
                    q.offerLast(now);
                    return true;
                }

//                if (q.isEmpty()) {
//                    map.remove(userId, q);
//                }

                return false;
            }
        }
    }

    static class UserReq {

        String userId;
        long timeStamp;

        public UserReq(String userId, long timeStamp) {
            this.userId = userId;
            this.timeStamp = timeStamp;
        }
    }

    static class Requests {

        int windowSec;
        int limit;
        List<UserReq> reqs;

        public Requests(int windowSec, int limit, List<UserReq> reqs) {
            this.windowSec = windowSec;
            this.limit = limit;
            this.reqs = reqs;
        }
    }

    public static void main(String[] args) {

        ArrayList<Requests> list = new ArrayList<>();

        list.add(new Requests(
                5,
                2,
                List.of(
                        new UserReq("u1", 1),
                        new UserReq("u1", 2),
                        new UserReq("u1", 5),
                        new UserReq("u1", 6)
                )
        ));

        for (Requests req : list) {
            rateLimitTest(req.windowSec, req.limit, req.reqs);
        }
    }

    public static void rateLimitTest(
            int windowSec,
            int limit,
            List<UserReq> requests) {

        RateLimiterStrategy rateLimiter =
                new SlidingWindowLog(
                        new RateLimiterConfig(limit, windowSec * 1000)
                );

        for (int i = 0; i < requests.size(); i++) {

            UserReq r = requests.get(i);

            if (rateLimiter.allow(r.userId, r.timeStamp)) {
                System.out.println("Request " + (i + 1) + " Allowed");
            } else {
                System.out.println(
                        "Sorry! status code 429: Request "
                                + (i + 1)
                                + " Rejected due to too many requests"
                );
            }
        }
    }
}