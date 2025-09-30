# Booking Service

The **Booking Service** is a Spring Boot microservice responsible for managing **court bookings** within sports arenas.  
It acts as the **orchestrator** between users and the **Court Service**, using **Kafka messaging** for asynchronous booking confirmation.

It is secured with **JWT authentication** and **role-based access control** for `ADMIN` and `CUSTOMER` users.

---

## Features

* **Create Booking**
  * Generates a unique booking ID
  * Persists booking with status `PENDING`
  * Publishes booking request to Kafka (`booking-requests`)
* **Consume Booking Responses**
  * Updates booking status to `CONFIRMED` or `REJECTED`
* **JWT authentication & authorization**
* **Kafka Producer/Consumer integration**
* **Database persistence** with PostgreSQL

---

## Tech Stack

* **Java** 17+
* **Spring Boot** 3.x
* **Spring Security + JWT**
* **Spring Data JPA** with PostgreSQL
* **Spring Kafka** (Producer + Consumer)
* **Eureka Client** for service discovery
* **Maven** for build & dependency management

---

## Getting Started

### Prerequisites

Ensure the following are installed and running:

* Java JDK 17+
* Maven 3.8+
* PostgreSQL 14+
* Apache Kafka + Zookeeper
* Eureka Service Registry running on port `8761`
* Court Service running

### Database Setup

Create a database in PostgreSQL:

```sql
CREATE DATABASE BookingsDatabase;
Update application.properties:

properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/BookingsDatabase
spring.datasource.username=postgres
spring.datasource.password=your_password
Run Locally
bash
Copy code
mvn clean install
mvn spring-boot:run
Service runs at:
👉 http://localhost:8086

Security & Roles
Admin

Create bookings

Delete bookings (future extension)

Customer

Create bookings

View booking details (future extension)

All endpoints require JWT authentication.

API Endpoints
Booking
Create Booking

http
Copy code
POST /book
Request Body:

json
Copy code
{
  "arenaId": 3,
  "courtId": 1,
  "date": "2025-09-08",
  "timeSlot": "10:00-11:00"
}
Response:

json
Copy code
{
  "bookingId": "f6e354ca-cf27-46da-9bbd-cdc39eb0d298",
  "userName": "abhi@gmail.com",
  "arenaId": 3,
  "courtId": 1,
  "date": "2025-09-08",
  "timeSlot": "10:00-11:00",
  "status": "PENDING"
}
Kafka Messaging
Topics
booking-requests (Producer)

booking-responses (Consumer)

Booking Flow
Booking Service receives booking request from user.

Persists booking in DB with status PENDING.

Publishes request to booking-requests.

Court Service processes the request:

If court is available → responds CONFIRMED.

If court is unavailable → responds REJECTED.

Booking Service consumes the response and updates booking status in DB.

Architecture Flow (Sequence Diagram)
mermaid
Copy code
sequenceDiagram
    participant U as User
    participant B as Booking Service
    participant K as Kafka
    participant C as Court Service
    participant DB as BookingRepository

    U->>B: POST /book (JWT + BookingRequest)
    B->>DB: Save booking (status=PENDING)
    B->>K: Publish BookingRequest
    K->>C: Deliver BookingRequest
    C->>K: Publish BookingResponse (CONFIRMED/REJECTED)
    K->>B: Deliver BookingResponse
    B->>DB: Update booking status
Models
Booking

bookingId, userName, arenaId, courtId, date, timeSlot, status

BookingRequest

bookingId, userName, arenaId, courtId, date, timeSlot

BookingResponse

bookingId, status

BookingStatus

PENDING, CONFIRMED, REJECTED, CANCELLED

Notes
Bookings are persisted in PostgreSQL.

Kafka ensures asynchronous booking confirmation.

JWT is validated for every API call.

Admins and Customers have role-based access to endpoints.

Designed to integrate seamlessly with the Court Service for availability and confirmation.
