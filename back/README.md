# MDD – Backend (Spring Boot)

This is the backend API for **MDD – Monde de Dév**, a minimal social network for developers.  
It provides endpoints for user authentication, topic subscriptions, article creation, commenting, and profile management.

---

## 🎯 Main Features

- User registration and login with JWT
- Topic subscription
- Article publication and filtering
- Comment system
- Profile and subscription management
- RESTful API consumed by Angular frontend

---

## 🛠️ Technologies Used

| Component     | Technology                               |
|---------------|-------------------------------------------|
| Language      | Java 21                                   |
| Framework     | Spring Boot, Spring Security, Spring Data JPA |
| Database      | PostgreSQL                                |
| Auth          | JWT (JSON Web Token)                      |
| Build Tool    | Maven                                     |

---

## 📦 Requirements

- Java 21+
- Maven
- PostgreSQL running locally

---

## 🧭 Clone the Repository

```bash
git clone https://github.com/natalliaSkiba/Developpez-une-application-full-stack-complete.git
cd Developpez-une-application-full-stack-complete/back
```

---

## ⚙️ Configuration (`application.yml`)

Create or update the file: `src/main/resources/application.yml` with your local settings:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mdd
    username: your_db_username
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080

jwt:
  secret: your_jwt_secret_key
```


## 🚀 How to Run

```bash
mvn clean install
mvn spring-boot:run
```

The backend will be available at:  
👉 `http://localhost:8080`

---

## 🔐 Authentication

- After login, users receive a JWT token
- All protected routes require the token in the header:

```
Authorization: Bearer <your_token>
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/openclassrooms/mddapi/
│   │       ├── config/         # JWT + security configuration  
│   │       ├── controller/     # REST endpoints  
│   │       ├── DTO/            # Data Transfer Objects  
│   │       ├── exception/      # Custom exception handling  
│   │       ├── model/          # JPA entities  
│   │       ├── repository/     # Data access  
│   │       ├── service/        # Business logic  
│   │       └── MddApiApplication.java  
│   └── resources/
│       └── application.yml
```

---

## 🔗 Related Projects

- [Frontend – Angular (MDD)](../front/README.md) – User interface for the MDD application
- [Figma Mockups](https://www.figma.com/design/Rflr3TVBog35BNMnn0DF09/Maquettes-MDD-(desktop-et-mobile)) – UI/UX design reference

---

## 👤 Author

**Natallia Skiba** – Full-Stack Developer (Java & Angular)
