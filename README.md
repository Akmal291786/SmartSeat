# SmartSeat – Payment-Confirmed Seat Reservation System

SmartSeat is a Spring Boot and MySQL based seat-reservation backend designed around a practical booking problem: **a seat should not become permanently booked until the associated payment is successfully completed.**

The project models a reservation as a short-lived `HELD` state during checkout. Only after a successful payment confirmation does the reservation become `CONFIRMED` and the seat move to `BOOKED`. Failed or abandoned checkout can therefore release the seat instead of creating a false booking.

> **Project period:** May 2025 – June 2025  
> **Author:** Akmal Ahmad Khan

## Problem Statement

Traditional seat-booking implementations can mark a seat as booked as soon as a user starts checkout. If payment is abandoned, rejected, or interrupted, the seat may remain unavailable even though no successful booking exists. This creates temporary seat blocking and reduces inventory availability.

SmartSeat separates **seat availability**, **reservation state**, and **payment confirmation** so that the final booking is created only after successful payment confirmation.

## Core Booking Flow

```text
AVAILABLE
    │
    │ Start checkout
    ▼
HELD / PENDING_PAYMENT
    │
    ├── Payment successful ──► CONFIRMED ──► BOOKED
    │
    └── Payment cancelled ──► CANCELLED ──► AVAILABLE
```

The important rule is:

**Payment success → reservation confirmation → seat becomes BOOKED.**

## Features

- Seat inventory management
- REST API based reservation workflow
- Temporary `HELD` state during payment
- Payment-confirmation based booking
- Prevention of double booking through server-side availability checks
- Reservation cancellation before confirmation
- MySQL persistence using Spring Data JPA
- Validation for customer and payment request data
- Seeded seat layout for quick development/testing
- Clear separation of entity, repository, service and controller layers

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Backend | Spring Boot |
| API | REST APIs |
| Database | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Build | Maven |

## Architecture

```text
Client / Booking UI
        │
        ▼
REST Controller
        │
        ▼
SeatService
        │
   ┌────┴───────────┐
   ▼                ▼
Repositories      Booking Rules
   │                │
   └───────┬────────┘
           ▼
         MySQL
```

### Main components

- `Seat` — stores seat number and availability state.
- `Reservation` — stores customer details, reservation state and payment reference.
- `Payment` — records successful payment confirmation.
- `SeatService` — contains the booking state-transition rules.
- `SeatController` — exposes REST endpoints.

## API Endpoints

### Get all seats

```http
GET /api/seats
```

### Start a reservation

```http
POST /api/reservations
Content-Type: application/json
```

```json
{
  "seatNumber": "A1",
  "customerName": "Akmal Khan",
  "customerEmail": "akmal@example.com"
}
```

The seat moves from `AVAILABLE` to `HELD`, while the reservation remains `PENDING_PAYMENT`.

### Confirm payment

```http
POST /api/reservations/{id}/payment
Content-Type: application/json
```

```json
{
  "paymentReference": "PAY-2025-001"
}
```

A successful payment confirmation changes the reservation to `CONFIRMED` and the seat to `BOOKED`.

### Cancel pending reservation

```http
POST /api/reservations/{id}/cancel
```

The held seat is released back to `AVAILABLE`.

### View reservations

```http
GET /api/reservations
```

## Database Model

### Seats

- `id`
- `seat_number`
- `status`

### Reservations

- `id`
- `seat_id`
- `customer_name`
- `customer_email`
- `status`
- `created_at`
- `payment_reference`

### Payments

- `id`
- `reservation_id`
- `reference`
- `status`
- `paid_at`

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Create the database

```sql
CREATE DATABASE smartseat;
```

### 2. Configure credentials

Update `src/main/resources/application.properties` or set environment variables:

```text
DB_URL=jdbc:mysql://localhost:3306/smartseat
DB_USERNAME=root
DB_PASSWORD=your_password
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The API starts at:

```text
http://localhost:8080
```

The application seeds a simple A1–F8 seat layout when the database is empty.

## Design Decisions

### Why a HELD state?

A checkout process needs a temporary state so that two customers cannot start checkout for the same available seat at the same time. The final `BOOKED` state is deliberately reserved for confirmed payments.

### Why server-side validation?

Seat availability and payment confirmation are business rules, so they must be enforced by the backend rather than relying only on frontend UI state.

### Why REST APIs?

The booking engine can be consumed by a web frontend, mobile application or another client without coupling the business logic to a specific UI.

## Future Enhancements

- Integrate a real payment gateway such as Razorpay or Stripe
- Add automatic expiry for abandoned `HELD` reservations
- WebSocket/SSE updates for real-time seat-map changes
- Authentication and role-based admin access
- Event/show-wise seat layouts
- Dynamic pricing by seat category
- Booking history and email confirmations
- Docker deployment and CI/CD

## Repository

**GitHub:** https://github.com/Akmal291786/SmartSeat

## License

This project is intended as an academic/portfolio project and can be extended for further development.
