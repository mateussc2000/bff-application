# bff-application

Backend for Frontend (**BFF**) application built with **Java 21** and **Spring Boot 3.5**.

This service acts as an API layer tailored for frontend consumption, centralizing:
- input validation
- response standardization
- orchestration with external systems (integration layer)
- error handling and observability

---

## What is a BFF in this project?

In this repository, the BFF is the backend boundary designed for client applications (web/mobile), abstracting downstream integrations and exposing a frontend-friendly contract.

Instead of frontend clients calling multiple backend systems directly, they call this BFF, which:
- validates and normalizes request parameters
- calls integration clients (via OpenFeign)
- maps integration payloads to API response DTOs
- returns consistent response envelopes

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.5**
- **Spring Web MVC**
- **Spring Cloud OpenFeign** (integration clients)
- **Spring Validation**
- **Spring Actuator**
- **Maven**
- **Lombok**

---

## Current Architecture (high level)

```text
src/main/java/com/bff/application/
├── BffApplication.java              # Spring Boot entrypoint + @EnableFeignClients
├── controller/                      # HTTP endpoints
├── facade/                          # Input validation / parameter normalization
├── model/
│   ├── dto/
│   │   ├── integration/             # External-system contracts
│   │   └── response/                # BFF API response contracts
│   └── mapper/                      # Integration DTO -> BFF response mapping
├── exception/                       # Domain/system custom exceptions
├── enums/                           # Error/status enums
├── utils/                           # Shared utility helpers
└── ... (other domain/service/integration packages)
```

---

## Request Flow

Typical request lifecycle:

1. **Controller** receives HTTP request.
2. **Facade** validates and sanitizes query/path/body params.
3. **Integration layer** calls downstream service(s) using Feign clients.
4. **Mapper** converts integration DTOs into BFF response DTOs.
5. **ResponseTemplate** wraps response in a consistent structure (`ok`, `data`, `error`).

---

## API Endpoints

### Health
- `GET /api/v1/health` → basic service health response.

### Product APIs
- `GET /api/v1/products`
- `GET /api/v1/products/{id}`
- `POST /api/v1/products`
- `PUT /api/v1/products/{id}`
- `DELETE /api/v1/products/{id}`

> Notes:
> - Listing endpoints may support filters/pagination according to current controller contract.
> - Actuator health endpoint is also available at: `GET /actuator/health`.

---

## Standard Response Envelope

The project uses a common response template pattern:

```json
{
  "ok": true,
  "data": { ... },
  "error": null
}
```

Error responses follow the same envelope with `ok: false` and a populated `error` object.

---

## Integration Layer

The integration layer is responsible for communication with external/downstream APIs and includes:

- Feign clients configured by `@EnableFeignClients`
- integration DTOs (`model.dto.integration`)
- mapping and fallback/error propagation into BFF exceptions

This ensures frontend consumers are isolated from downstream payload formats and transient integration concerns.

---

## Error Handling

Custom exceptions and error codes are centralized (e.g., mapping, integration, validation, business, token, system errors), enabling:

- predictable error payloads
- easier tracing and observability
- consistent status/error semantics across endpoints

---

## Running Locally

### Prerequisites
- Java 21+
- Maven 3.9+

### Run

```bash
mvn spring-boot:run
```

Default URL: `http://localhost:8080`

### Run tests

```bash
mvn test
```

### Build

```bash
mvn clean package -DskipTests
java -jar target/bff-application-0.0.1-SNAPSHOT.jar
```

---

## Containerization

### Build image

```bash
docker build -t bff-application .
```

### Run container

```bash
docker run -p 8080:8080 bff-application
```

---

## Observability

- Application health: `GET /api/v1/health`
- Actuator health: `GET /actuator/health`

For production, prefer externalized configuration (env vars/secrets) and hardened integration timeouts/retries/circuit-breaking as needed.
