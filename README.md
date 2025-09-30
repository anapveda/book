Booking Service
The Booking Service is a Spring Boot microservice responsible for managing court bookings.
It acts as the orchestrator between users and the Court Service using Kafka messaging for asynchronous booking confirmation.
The service also enforces JWT authentication and role-based access control.
________________________________________
Features
•	Create Booking:
o	Generates a unique booking ID
o	Persists booking with status PENDING
o	Publishes booking request to Kafka (booking-requests)
•	Consume Booking Responses:
o	Updates booking status to CONFIRMED or REJECTED
•	JWT authentication & authorization:
o	Admin and Customer roles supported
•	Kafka Consumer/Producer integration
•	Database persistence with PostgreSQL
________________________________________
Tech Stack
•	Java: 17+
•	Spring Boot: 3.x
•	Spring Security + JWT
•	Spring Data JPA with PostgreSQL
•	Spring Kafka (Producer + Consumer)
•	Eureka Client for service discovery
•	Maven for build & dependency management
________________________________________
Getting Started
Prerequisites
Make sure you have installed:
•	Java JDK 17+
•	Maven 3.8+
•	PostgreSQL 14+
•	Apache Kafka + Zookeeper
•	Eureka Service Registry running on port 8761
•	Court Service running
Database Setup
Create a database in PostgreSQL:
CREATE DATABASE BookingsDatabase;
Update application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/BookingsDatabase
spring.datasource.username=postgres
spring.datasource.password=your_password
Run Locally
mvn clean install
mvn spring-boot:run
Service runs at:
👉 http://localhost:8086
________________________________________
Security & Roles
•	Admin:
o	Create bookings
o	Delete bookings (if extended)
•	Customer:
o	Create bookings
o	View booking details (future extension)
•	All endpoints require JWT authentication.
________________________________________
API Endpoints
Booking
•	Create Booking
•	POST /book
Request Body:
postman request:http://localhost:8086/book
{
  "arenaId": 3,
  "courtId": 1,
  "date": "2025-09-08",
  "timeSlot": "10:00-11:00"
}
response:
{
    "bookingId": "f6e354ca-cf27-46da-9bbd-cdc39eb0d298",
    "userName": "abhi@gmail.com",
    "arenaId": 3,
    "courtId": 1,
    "date": "2025-09-08",
    "timeSlot": "10:00-11:00",
    }
    
Kafka Messaging
Topics
•	booking-requests (Producer)
•	booking-responses (Consumer)
Booking Flow
1.	Booking Service receives booking request from user.
2.	Persists booking in DB with status PENDING.
3.	Publishes request to booking-requests.
4.	Court Service processes the request:
o	If available → responds CONFIRMED
o	If unavailable → responds REJECTED
5.	Booking Service consumes response and updates booking status in DB.
________________________________________
Architecture Flow (Sequence Diagram)
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
    B->>DB: Update booking stat

