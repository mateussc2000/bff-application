# bff-application

A **Backend for Frontend (BFF)** application built with **Java 21** and **Spring Boot 3.5** following the **MVC (Model-View-Controller)** architecture pattern, ready for deployment on **AWS ECS**.

## Tech Stack

- **Java 21** (LTS)
- **Spring Boot 3.5** (latest LTS)
- **Spring Web MVC** – REST API layer
- **Spring Data JPA** – Data access layer
- **Spring Actuator** – Health checks for ECS
- **Spring Validation** – Request validation
- **H2** – In-memory database (development)
- **Lombok** – Boilerplate reduction
- **Maven** – Build tool

## Project Structure (MVC)

```
src/main/java/com/bff/application/
├── BffApplication.java          # Entry point
├── controller/                  # MVC: Controller layer (REST endpoints)
│   ├── HealthController.java
│   └── ProductController.java
├── service/                     # MVC: Service layer (business logic)
│   └── ProductService.java
├── repository/                  # MVC: Repository layer (data access)
│   └── ProductRepository.java
├── model/
│   ├── entity/                  # JPA entities
│   │   └── Product.java
│   └── dto/                     # Data Transfer Objects
│       ├── ApiResponse.java
│       ├── ProductRequest.java
│       └── ProductResponse.java
├── exception/                   # Exception handling
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── config/                      # Spring configuration
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/health` | Application health check |
| GET | `/api/v1/products` | List all products (supports `?name=` filter) |
| GET | `/api/v1/products/{id}` | Get product by ID |
| POST | `/api/v1/products` | Create a new product |
| PUT | `/api/v1/products/{id}` | Update a product |
| DELETE | `/api/v1/products/{id}` | Delete a product |

Spring Actuator is available at `/actuator/health`.

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+

### Run with Maven

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

### Run Tests

```bash
mvn test
```

### Build JAR

```bash
mvn clean package -DskipTests
java -jar target/bff-application-0.0.1-SNAPSHOT.jar
```

## Docker / ECS

### Build Docker Image

```bash
docker build -t bff-application .
```

### Run Docker Container

```bash
docker run -p 8080:8080 bff-application
```

The Dockerfile uses a **multi-stage build** with a non-root user for ECS security best practices.
