# Prompt: 

## Create classes for event ticket booking system including

1. Strategy pattern
2. Locking strategy
3. Atomic operations wherever required
4. Chain of Filter pattern
5. Repository to store in memory data structures


# Response

Below is a **clean interview-level LLD skeleton** for an **Event Ticket Booking System** implementing the patterns you asked for.

Patterns included:

1. **Strategy Pattern** → pricing / seat allocation
2. **Locking Strategy** → seat locking abstraction
3. **Atomic operations** → concurrency safe seat booking
4. **Chain of Responsibility (Filter Chain)** → validation pipeline
5. **Repository Pattern** → in-memory storage using `Map<UUID, Entity>`

This is structured the way **senior engineers structure machine coding solutions**.

---

# 1️⃣ Domain Entities

## User

```java
class User {

    UUID id;
    String name;
    String email;

}
```

---

## Event

```java
class Event {

    UUID id;
    String name;
    String description;

}
```

---

## Show

```java
class Show {

    UUID id;

    UUID eventId;
    UUID roomId;

    Instant startTime;

}
```

---

## Room

```java
class Room {

    UUID id;

    String name;

}
```

---

## Seat

```java
class Seat {

    UUID id;

    UUID roomId;

    String seatNumber;

}
```

---

## ShowSeat (critical entity)

```java
class ShowSeat {

    UUID id;

    UUID showId;
    UUID seatId;

    AtomicReference<SeatStatus> status =
            new AtomicReference<>(SeatStatus.AVAILABLE);

    UUID lockedBy;
    Instant lockExpiry;

}
```

---

## SeatStatus

```java
enum SeatStatus {

    AVAILABLE,
    LOCKED,
    BOOKED

}
```

---

## Booking

```java
class Booking {

    UUID id;

    UUID userId;
    UUID showId;

    List<UUID> showSeatIds;

    BookingStatus status;

}
```

---

## BookingStatus

```java
enum BookingStatus {

    CREATED,
    CONFIRMED,
    CANCELLED

}
```

---

## Ticket

```java
class Ticket {

    UUID id;

    UUID bookingId;

    Instant issuedAt;

}
```

---

# 2️⃣ Repository Pattern (In-Memory Storage)

Repositories store:

```text
Map<UUID, Entity>
```

---

## EventRepository

```java
class EventRepository {

    Map<UUID, Event> events = new ConcurrentHashMap<>();

    void save(Event event) {
        events.put(event.id, event);
    }

    Event find(UUID id) {
        return events.get(id);
    }

}
```

---

## ShowRepository

```java
class ShowRepository {

    Map<UUID, Show> shows = new ConcurrentHashMap<>();

    void save(Show show) {
        shows.put(show.id, show);
    }

    Show find(UUID id) {
        return shows.get(id);
    }

}
```

---

## ShowSeatRepository

```java
class ShowSeatRepository {

    Map<UUID, ShowSeat> showSeats = new ConcurrentHashMap<>();

    ShowSeat find(UUID id) {
        return showSeats.get(id);
    }

    void save(ShowSeat seat) {
        showSeats.put(seat.id, seat);
    }

}
```

---

## BookingRepository

```java
class BookingRepository {

    Map<UUID, Booking> bookings = new ConcurrentHashMap<>();

    void save(Booking booking) {
        bookings.put(booking.id, booking);
    }

}
```

---

# 3️⃣ Strategy Pattern

Used for **pricing and seat allocation logic**.

---

## PricingStrategy

```java
interface PricingStrategy {

    double calculatePrice(Show show, List<ShowSeat> seats);

}
```

---

## FlatPricingStrategy

```java
class FlatPricingStrategy implements PricingStrategy {

    public double calculatePrice(Show show, List<ShowSeat> seats) {

        return seats.size() * 200;

    }

}
```

---

## PremiumSeatPricingStrategy

```java
class PremiumSeatPricingStrategy implements PricingStrategy {

    public double calculatePrice(Show show, List<ShowSeat> seats) {

        return seats.size() * 400;

    }

}
```

---

# 4️⃣ Locking Strategy

Abstract locking so system can switch between:

* in-memory lock
* Redis lock
* DB lock

---

## LockingStrategy

```java
interface LockingStrategy {

    boolean lock(ShowSeat seat, UUID userId);

    void unlock(ShowSeat seat);

}
```

---

## AtomicSeatLockStrategy

Uses **atomic CAS operation**.

```java
class AtomicSeatLockStrategy implements LockingStrategy {

    public boolean lock(ShowSeat seat, UUID userId) {

        boolean success = seat.status.compareAndSet(
                SeatStatus.AVAILABLE,
                SeatStatus.LOCKED
        );

        if (success) {

            seat.lockedBy = userId;
            seat.lockExpiry = Instant.now().plusSeconds(300);

        }

        return success;

    }

    public void unlock(ShowSeat seat) {

        seat.status.set(SeatStatus.AVAILABLE);

        seat.lockedBy = null;

    }

}
```

---

# 5️⃣ Chain of Responsibility (Filter Chain)

Used for **booking validation pipeline**.

Example validations:

* seat exists
* seat available
* lock not expired

---

## BookingFilter

```java
interface BookingFilter {

    void execute(BookingContext context);

}
```

---

## BookingContext

```java
class BookingContext {

    UUID userId;

    List<ShowSeat> seats;

}
```

---

## SeatAvailabilityFilter

```java
class SeatAvailabilityFilter implements BookingFilter {

    public void execute(BookingContext context) {

        for (ShowSeat seat : context.seats) {

            if (seat.status.get() != SeatStatus.AVAILABLE) {

                throw new RuntimeException("Seat unavailable");

            }

        }

    }

}
```

---

## SeatLockFilter

```java
class SeatLockFilter implements BookingFilter {

    LockingStrategy lockingStrategy;

    public SeatLockFilter(LockingStrategy strategy) {
        this.lockingStrategy = strategy;
    }

    public void execute(BookingContext context) {

        for (ShowSeat seat : context.seats) {

            boolean locked = lockingStrategy.lock(seat, context.userId);

            if (!locked) {
                throw new RuntimeException("Seat lock failed");
            }

        }

    }

}
```

---

## FilterChain

```java
class FilterChain {

    List<BookingFilter> filters = new ArrayList<>();

    void addFilter(BookingFilter filter) {
        filters.add(filter);
    }

    void execute(BookingContext context) {

        for (BookingFilter filter : filters) {
            filter.execute(context);
        }

    }

}
```

---

# 6️⃣ BookingService

Coordinates everything.

```java
class BookingService {

    ShowSeatRepository showSeatRepo;

    BookingRepository bookingRepo;

    PricingStrategy pricingStrategy;

    FilterChain filterChain;

    TicketService ticketService;

    Booking createBooking(UUID userId, List<UUID> seatIds, UUID showId) {

        List<ShowSeat> seats = seatIds.stream()
                .map(showSeatRepo::find)
                .toList();

        BookingContext context = new BookingContext();
        context.userId = userId;
        context.seats = seats;

        filterChain.execute(context);

        Booking booking = new Booking();

        booking.id = UUID.randomUUID();
        booking.userId = userId;
        booking.showId = showId;

        booking.showSeatIds = seatIds;

        booking.status = BookingStatus.CONFIRMED;

        bookingRepo.save(booking);

        ticketService.generateTicket(booking);

        return booking;

    }

}
```

---

# 7️⃣ TicketService

```java
class TicketService {

    TicketRepository ticketRepository;

    void generateTicket(Booking booking) {

        Ticket ticket = new Ticket();

        ticket.id = UUID.randomUUID();

        ticket.bookingId = booking.id;

        ticket.issuedAt = Instant.now();

        ticketRepository.save(ticket);

    }

}
```

---

# 8️⃣ Background Worker (Seat Lock Expiry)

```java
class SeatLockExpiryWorker {

    ShowSeatRepository repository;

    void releaseExpiredLocks() {

        for (ShowSeat seat : repository.showSeats.values()) {

            if (seat.status.get() == SeatStatus.LOCKED &&
                    seat.lockExpiry.isBefore(Instant.now())) {

                seat.status.set(SeatStatus.AVAILABLE);

                seat.lockedBy = null;

            }

        }

    }

}
```

---

# 9️⃣ Final Architecture

```text
Controller
   ↓
BookingService
   ↓
FilterChain
   ↓
LockingStrategy
   ↓
Repositories
   ↓
ConcurrentHashMap Storage
```

---

# 🔟 What Interviewers Like in This Design

This design demonstrates:

✔ Repository Pattern
✔ Strategy Pattern
✔ Chain of Responsibility
✔ Concurrency handling
✔ Atomic operations
✔ Lock expiry worker
✔ Separation of domain / service / persistence

This is **exactly the level expected in senior LLD interviews**.

---


