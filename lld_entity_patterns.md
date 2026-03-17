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




Great. Now I’ll show you the 12 most reusable entities across almost every LLD interview problem.

This is extremely powerful because once you recognize these patterns, you can design systems like Uber, Parking Lot, Ticket Booking, Food Delivery, Elevator, Ecommerce, Messaging very quickly.

I will also show typical fields so you can compare entities across problems as you requested.

⸻

1️⃣ Actor Entity (User / Participant)

Represents any person interacting with the system.

Examples:

System	Actor
Ticket Booking	User
Uber	Rider, Driver
Ecommerce	Customer, Seller
Food Delivery	Customer, DeliveryAgent
Parking Lot	Driver

Typical Fields

class User {

    UUID id;

    String name;

    String email;

    String phone;

    Instant createdAt;

    AccountStatus status;

}

Reusable fields across systems:

id
name
email
phone
status
createdAt


⸻

2️⃣ Resource Entity (Limited Capacity Object)

Represents a scarce physical resource.

Examples:

System	Resource
Ticket Booking	Seat
Parking Lot	ParkingSpot
Hotel Booking	Room
Uber	Driver
Food Delivery	DeliveryAgent

Typical Fields

class Resource {

    UUID id;

    String resourceNumber;

    ResourceStatus status;

    UUID parentId;

}

Example Seat:

class Seat {

    UUID id;

    UUID roomId;

    String seatNumber;

}


⸻

3️⃣ Core Domain Entity

The main object around which the system revolves.

Examples:

System	Core
Ticket Booking	Event
Uber	Ride
Ecommerce	Product
Food Delivery	Restaurant
Parking Lot	ParkingLot

Typical Fields

class DomainObject {

    UUID id;

    String name;

    String description;

    Instant createdAt;

}


⸻

4️⃣ Context Entity

Represents a time-based or situation-based context.

Examples:

System	Context
Ticket Booking	Show
Uber	Trip
Hotel Booking	Stay
Food Delivery	OrderSession

Typical Fields

class Context {

    UUID id;

    UUID domainId;

    Instant startTime;

    Instant endTime;

}

Example Show:

class Show {

    UUID id;

    UUID eventId;

    UUID roomId;

    Instant startTime;

}


⸻

5️⃣ Mapping Entity

Represents relationship between resource and context.

Very important in booking systems.

Examples:

System	Mapping
Ticket Booking	ShowSeat
Hotel Booking	RoomAvailability
Inventory	ProductInventory
Ride Sharing	DriverLocation

Typical Fields

class ResourceMapping {

    UUID id;

    UUID resourceId;

    UUID contextId;

    ResourceStatus status;

}

Example:

class ShowSeat {

    UUID id;

    UUID showId;

    UUID seatId;

    SeatStatus status;

}


⸻

6️⃣ Workflow State Entity

Represents a temporary process.

Examples:

System	Workflow
Ticket Booking	Booking
Uber	Trip
Ecommerce	Order
Food Delivery	Order

Typical Fields

class Workflow {

    UUID id;

    UUID userId;

    List<UUID> resourceIds;

    WorkflowStatus status;

    Instant createdAt;

}

Example:

class Booking {

    UUID id;

    UUID userId;

    UUID showId;

    List<UUID> showSeatIds;

    BookingStatus status;

}


⸻

7️⃣ Transaction Entity

Handles payments or financial operations.

Examples:

System	Transaction
Ticket Booking	Payment
Uber	FarePayment
Ecommerce	Payment
Food Delivery	Payment

Typical Fields

class Payment {

    UUID id;

    UUID referenceId;

    double amount;

    PaymentStatus status;

    PaymentMethod method;

    Instant createdAt;

}


⸻

8️⃣ Output Entity

Represents the final result generated by system.

Examples:

System	Output
Ticket Booking	Ticket
Uber	RideReceipt
Ecommerce	Invoice
Food Delivery	DeliveryReceipt

Typical Fields

class Ticket {

    UUID id;

    UUID bookingId;

    Instant issuedAt;

}


⸻

9️⃣ Inventory Entity

Represents quantity tracking.

Examples:

System	Inventory
Ecommerce	ProductInventory
Warehouse	Stock
Ticket Booking	SeatAvailability

Typical Fields

class Inventory {

    UUID id;

    UUID itemId;

    int total;

    int available;

}


⸻

🔟 Queue / Request Entity

Represents incoming requests waiting for processing.

Examples:

System	Request
Elevator	ElevatorRequest
Ride Sharing	RideRequest
Food Delivery	OrderRequest

Typical Fields

class Request {

    UUID id;

    UUID userId;

    RequestType type;

    Instant requestedAt;

}


⸻

1️⃣1️⃣ Assignment Entity

Represents allocation of resource to request.

Examples:

System	Assignment
Uber	DriverAssignment
Food Delivery	DeliveryAssignment
Parking Lot	SpotAssignment

Typical Fields

class Assignment {

    UUID id;

    UUID requestId;

    UUID resourceId;

    AssignmentStatus status;

}


⸻

1️⃣2️⃣ Audit / Log Entity

Tracks system events.

Examples:

System	Audit
Payments	PaymentLog
Orders	OrderHistory
Booking	BookingHistory

Typical Fields

class AuditLog {

    UUID id;

    String action;

    UUID entityId;

    Instant timestamp;

}


⸻

🧠 Complete Universal Entity Map

Actor
User / Driver / Customer

Core
Event / Product / Ride / Restaurant

Resource
Seat / Room / ParkingSpot

Context
Show / Trip / Stay

Mapping
ShowSeat / RoomAvailability

Workflow
Booking / Order / Trip

Transaction
Payment

Output
Ticket / Receipt / Invoice

Inventory
Stock / SeatAvailability

Request
RideRequest / ElevatorRequest

Assignment
DriverAssignment / SpotAssignment

Audit
Logs / History


⸻

🧠 Interview Superpower

When given any LLD problem, run this checklist:

1 Actor
2 Core domain
3 Resource
4 Context
5 Mapping
6 Workflow
7 Transaction
8 Output

You instantly discover 90% of the entities.

⸻

⭐ Example: Ticket Booking Using This Framework

Actor
User

Core
Event

Context
Show

Resource
Seat
Room

Mapping
ShowSeat

Workflow
Booking

Transaction
Payment

Output
Ticket


⸻

If you want, I can also show you something extremely useful for interviews:

The 6 hidden entities most candidates miss in LLD problems (including FAANG candidates).

Once you learn those, your designs become much more senior-level.
