Excellent. Here is the **second table** mapping the **8 common LLD interview problems** to the **design patterns most commonly used** and *why they are used*.
This helps you answer the typical interviewer question:

> *“Which design patterns would you use here and why?”*

---

# Design Patterns Used in Common LLD Problems

| Problem                 | Strategy Pattern                             | Observer Pattern                     | Factory Pattern                | State Pattern                                    | Decorator Pattern                | Chain of Responsibility      | Singleton          | Notes                         |
| ----------------------- | -------------------------------------------- | ------------------------------------ | ------------------------------ | ------------------------------------------------ | -------------------------------- | ---------------------------- | ------------------ | ----------------------------- |
| **Parking Lot**         | Spot allocation strategy (nearest, cheapest) | Notify admin when lot full           | Create different vehicle types | Parking spot state (AVAILABLE, OCCUPIED)         | Add pricing features dynamically | Entry validation pipeline    | ParkingLot manager | Focus: allocation algorithms  |
| **Ticket Booking**      | Pricing strategy, seat allocation            | Notify users when seats released     | Ticket creation                | Seat states (AVAILABLE, LOCKED, BOOKED)          | Retry payment wrapper            | Booking validation filters   | BookingService     | Concurrency heavy system      |
| **Ride Sharing (Uber)** | Driver matching algorithm                    | Driver location updates              | Vehicle creation               | Ride lifecycle (REQUESTED → STARTED → COMPLETED) | Surge pricing wrapper            | Request filtering            | DispatchManager    | Matching algorithms important |
| **Food Delivery**       | Delivery assignment algorithm                | Order status notifications           | Restaurant/menu creation       | Order states                                     | Delivery pricing add-ons         | Order validation chain       | OrderManager       | Logistics coordination        |
| **Elevator System**     | Elevator scheduling algorithm (SCAN etc.)    | Notify floors about elevator arrival | Elevator creation              | Elevator state (MOVING, IDLE)                    | Priority scheduling wrappers     | Request routing              | ElevatorController | Scheduling algorithms         |
| **Ecommerce**           | Discount strategy, shipping strategy         | Order updates to customers           | Product factory                | Order lifecycle                                  | Coupon/discount wrappers         | Checkout validation pipeline | InventoryManager   | Many extension points         |
| **Messaging System**    | Message routing strategy                     | Message delivered notifications      | Message type creation          | Message lifecycle                                | Encryption layers                | Message filters              | MessagingService   | Event driven architecture     |
| **Rate Limiter**        | Rate limiting algorithm (token bucket etc.)  | Monitoring alerts                    | Limiter type creation          | Request states                                   | Logging wrapper                  | Request validation chain     | LimiterManager     | Performance critical          |

---

# Most Important Patterns Across These Problems

The patterns that appear **again and again**:

| Pattern                 | Why It Appears Often              |
| ----------------------- | --------------------------------- |
| Strategy                | Multiple algorithms for same task |
| Factory                 | Object creation abstraction       |
| State                   | Entities with lifecycle           |
| Observer                | Event notifications               |
| Chain of Responsibility | Validation pipelines              |
| Singleton               | Central managers                  |
| Decorator               | Extending behavior dynamically    |

These **7 patterns cover ~90% of LLD interviews**.

---

# Example: Ticket Booking Pattern Mapping

```id="vm1vns"
Strategy → PricingStrategy
State → SeatStatus
Factory → TicketFactory
Observer → BookingNotifications
ChainOfResponsibility → BookingFilters
Decorator → RetryPaymentDecorator
Singleton → BookingService
```

---

# Interview Tip

When explaining patterns, say:

> “I would use **Strategy Pattern** for interchangeable algorithms such as pricing or seat allocation, **State Pattern** for seat lifecycle management, and **Chain of Responsibility** to validate booking requests step by step.”

This shows **architectural thinking**, not just class design.

---

# If you'd like, I can also show something extremely useful next:

**The “LLD Pattern Cheat Sheet”** — a single-page mapping of **all major design patterns to the problems where they naturally appear in interviews.**

It makes remembering patterns **much easier under interview pressure.**
