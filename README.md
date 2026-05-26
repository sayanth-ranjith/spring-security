# Spring Security Demo

A simple Java Spring Boot project demonstrating database-based authentication using Spring Security with BCrypt password encoding.

This project is intended as a starter template for learning and building secure Spring Boot applications.

---

## Features

- Spring Boot + Spring Security integration
- Database-level authentication
- BCrypt password encoding
- User registration API
- Secured APIs by default
- Public endpoint support
- Easy local database setup
- Automatic table creation support using JPA/Hibernate

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- BCrypt Password Encoder
- H2 / PostgreSQL / MySQL (configurable)

---

## Authentication Rules

### Public APIs

The following endpoint is accessible without authentication:

```text
/generate/app/**
```

Example:

```text
/generate/app/register/user
```

### Secured APIs

All other endpoints require authentication.

---

## User Registration

Register a user using:

```http
POST /generate/app/register/user
```

### Sample Request

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Notes

- Passwords are stored using BCrypt encoding
- Authentication happens against database records

---

## Database Setup

The project currently supports a local database setup.

You can modify database configuration in:

```properties
application.properties
```

Example:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

---

## Auto Table Creation

You can enable automatic table creation using Hibernate/JPA.

Example:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This will automatically create the required tables during application startup.

Suggested user table:

```text
user_cred_info
```

---

## Running the Project

### Clone Repository

```bash
git clone https://github.com/sayanth-ranjith/spring-security.git
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Default Flow

1. Start application
2. Register user using public API
3. Login using registered credentials
4. Access secured APIs

---

## Future Improvements

- JWT Authentication
- Role-based authorization
- Refresh tokens
- OAuth2 / OIDC integration
- Docker support
- PostgreSQL production setup
- Swagger/OpenAPI documentation

---

## Learning Purpose

This project is mainly created to understand:

- Spring Security fundamentals
- Authentication flow
- Password encoding
- Security filter chain
- Database-backed user authentication

---

## Author

GitHub: https://github.com/sayanth-ranjith/spring-security
