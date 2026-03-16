Below is a **structured table** mapping the **8 most common LLD interview problems** to the **LLD Blueprint categories** we discussed.
This helps you quickly identify **entities, locks, and workers** when a problem appears in an interview.

---

# Common LLD Interview Problems Using the Blueprint

| Problem                         | Actors                                   | Core Domain    | Context       | Resources                | Mapping Entity          | Workflow Entity   | Transaction | Output                | Lock Entity          | Worker                   |
| ------------------------------- | ---------------------------------------- | -------------- | ------------- | ------------------------ | ----------------------- | ----------------- | ----------- | --------------------- | -------------------- | ------------------------ |
| **Parking Lot**                 | Driver, Admin                            | ParkingLot     | ParkingFloor  | ParkingSpot              | SpotAssignment          | ParkingTicket     | Payment     | Receipt               | SpotLock             | ExpiredTicketCleaner     |
| **Ticket Booking (BookMyShow)** | User, Admin                              | Event          | Show          | Seat, Room               | ShowSeat                | Booking           | Payment     | Ticket                | SeatLock             | SeatLockExpiryWorker     |
| **Ride Sharing (Uber)**         | Rider, Driver                            | Ride           | Trip          | Driver, Vehicle          | DriverLocation          | RideRequest       | FarePayment | RideReceipt           | DriverAssignmentLock | DriverLocationUpdater    |
| **Food Delivery (Swiggy)**      | Customer, DeliveryAgent, RestaurantOwner | Restaurant     | OrderSession  | MenuItem, DeliveryAgent  | MenuAvailability        | Order             | Payment     | OrderReceipt          | DeliveryAgentLock    | DeliveryAssignmentWorker |
| **Elevator System**             | Passenger, Admin                         | ElevatorSystem | ElevatorCar   | Elevator, Floor          | FloorRequest            | ElevatorRequest   | -           | RequestCompletedEvent | ElevatorDoorLock     | ElevatorScheduler        |
| **Ecommerce System**            | Customer, Seller, Admin                  | Product        | CartSession   | InventoryItem, Warehouse | ProductInventory        | Order             | Payment     | Invoice               | InventoryLock        | OrderProcessingWorker    |
| **Messaging System (WhatsApp)** | User                                     | Conversation   | ChatSession   | Message                  | ConversationParticipant | MessageDelivery   | -        | DeliveryReceipt       | MessageSequenceLock  | MessageDispatcher        |
| **Rate Limiter**                | API Client                               | RateLimiter    | RequestWindow | TokenBucket              | ClientRateLimit         | RequestProcessing | -        | RateLimitResponse     | CounterLock          | TokenRefillWorker        |

---

# How to Use This Table in Interviews

When given an LLD problem:

1. **Identify Actors**
2. **Identify Core Domain**
3. **Identify Context**
4. **Identify Limited Resources**
5. **Check if Resource Availability depends on Context**
6. **Identify Workflow**
7. **Check if Payment/Transaction exists**
8. **Identify Output**
9. **Identify Concurrency Locks**
10. **Identify Background Workers**

Example for **Ticket Booking**:

```
Actor → User
Core → Event
Context → Show
Resource → Seat
Mapping → ShowSeat
Workflow → Booking
Transaction → Payment
Output → Ticket
Lock → SeatLock
Worker → SeatLockExpiryWorker
```

This quickly produces a **complete entity model**.

---

# Pattern Recognition Tip

Across almost all LLD problems:

```
Actors
Core Domain
Context
Resources
Mapping
Workflow
Transaction
Output
Lock
Worker
```

These **10 categories cover ~90% of real-world systems**.

---

If you'd like, I can also create a **second table showing the design patterns (Strategy, Observer, Factory, etc.) most commonly used in each of these 8 problems**, which helps during interviews when they ask **“which design pattern would you apply?”**.
