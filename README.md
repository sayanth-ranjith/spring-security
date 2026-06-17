# Spring Security JWT Demo

A Java Spring Boot project demonstrating **JWT-based authentication using Spring Security with database-backed users and BCrypt password encoding**.

This project is a learning starter template for building secure, stateless authentication systems using Spring Security + JWT.

---

## 🚀 Key Upgrade

This project has moved from session/basic authentication to **JWT-based authentication**.

Now all secured APIs require a valid JWT token.

---

## 🔐 Authentication Flow

### 1. User Registration (Public)

Register a new user in the system.

**Endpoint:**

```
POST /generate/app/register/user
```

**Request:**

```json
{
  "username": "admin",
  "password": "admin123",
  "roles": "USER"
}
```

Notes:

- Password is stored using BCrypt encryption
- User details are stored in database
- Roles are stored for future role-based authorization

### 2. Login (Token Generation)

Authenticate user and generate JWT token.

**Endpoint:**

```
POST /generate/app/login
```

**Request:**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response:**

```json
{
  "username": "admin",
  "jwtToken": "eyJhbGciOiJIUzI1NiJ9...",
  "loginComment": "Login successful"
}
```

---

## 🔑 Using the JWT Token

All secured APIs require the token in request header:

```
INTENDED_TOKEN: <generated-jwt-token>
```

**Example:**

```
GET /getData
INTENDED_TOKEN: eyJhbGciOiJIUzI1NiJ9...
```

---

## 🔒 Security Rules

### Public Endpoints

No authentication required:

```
/generate/app/**
```

Includes:

- User registration
- Login

### Secured Endpoints

All other endpoints require valid JWT token:

```
/**
```

---

## ⚙️ JWT Validation Flow

1. User registers (stored in DB)
2. User logs in with username & password
3. System validates credentials against database
4. If valid → JWT token is generated
5. Client sends token in `INTENDED_TOKEN` header
6. Spring Security filter validates token
7. Request is allowed if token is valid

---

## 🗄 Database Setup

Supports H2 / MySQL / PostgreSQL.

Configure in:

```
application.properties
```

**Example:**

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

---

## 👤 User Table

Suggested table:

```
user_cred_info
```

Fields:

- username
- password (BCrypt encoded)
- roles

---

## ▶️ Running the Project

**Clone Repository**

```bash
git clone https://github.com/sayanth-ranjith/spring-security.git
```

**Build Project**

```bash
mvn clean install
```

**Run Application**

```bash
mvn spring-boot:run
```

---

## 🧠 Learning Goals

This project helps you understand:

- Spring Security filter chain
- JWT authentication flow
- Stateless authentication
- BCrypt password encoding
- Database-backed authentication
- Secure API design

---

## 🔮 Future Improvements

- Role-based authorization (ADMIN / USER)
- Refresh tokens
- OAuth2 / OIDC integration
- API documentation (Swagger/OpenAPI)
- Docker deployment
- Production-ready DB setup
