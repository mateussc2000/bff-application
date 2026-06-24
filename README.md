# bff-application

A **Backend for Frontend (BFF)** application built with **Java 21** and **Spring Boot 3.5**, following the **MVC (Model-View-Controller)** architecture pattern and prepared for deployment on **AWS ECS**.

## Overview

This project exposes REST endpoints to manage products with:

- Input validation
- Standardized API responses
- Exception handling
- Health checks for observability

It is designed to be simple to run locally and containerize for cloud environments.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5**
- **Spring Web MVC** (REST API)
- **Spring Data JPA** (persistence)
- **Spring Validation** (request validation)
- **Spring Actuator** (health/metrics endpoints)
- **H2** (in-memory database for development)
- **Lombok**
- **Maven**
- **Docker**

## Project Structure (MVC)

```text
src/main/java/com/bff/application/
├── BffApplication.java          # Entry point
├── controller/                  # REST controllers
│   ├── HealthController.java
│   └── ProductController.java
├── service/                     # Business rules
│   └── ProductService.java
├── repository/                  # Data access
│   └── ProductRepository.java
├── model/
│   ├── entity/                  # JPA entities
│   │   └── Product.java
│   └── dto/                     # Request/response DTOs
│       ├── ApiResponse.java
│       ├── ProductRequest.java
│       └── ProductResponse.java
├── exception/                   # Error handling
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── config/                      # Application configuration
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/health` | Application health check |
| GET | `/api/v1/products` | List products (optional `?name=` filter) |
| GET | `/api/v1/products/{id}` | Get a product by ID |
| POST | `/api/v1/products` | Create a product |
| PUT | `/api/v1/products/{id}` | Update a product |
| DELETE | `/api/v1/products/{id}` | Delete a product |

Actuator health endpoint: `/actuator/health`

## Running Locally

### Prerequisites

- **Java 21+**
- **Maven 3.9+**

### Start application

```bash
mvn spring-boot:run
```

Application URL: `http://localhost:8080`

### Run tests

```bash
mvn test
```

### Build executable JAR

```bash
mvn clean package -DskipTests
java -jar target/bff-application-0.0.1-SNAPSHOT.jar
```

## Docker

### Build image

```bash
docker build -t bff-application .
```

### Run container

```bash
docker run -p 8080:8080 bff-application
```

## AWS ECS Notes

The Docker image is built with a **multi-stage Dockerfile** and runs as a **non-root user**, which is aligned with common ECS security best practices.

For production deployments, prefer externalized configuration (environment variables / secrets manager) and a persistent production-grade database.
