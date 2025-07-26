# 🚆 Train Ticket Reservation System

A full-stack backend system built using *Java, **Spring Boot, and **H2 Database*, following:
- ✅ MVC Architecture
- ✅ SOLID & GRASP Design Principles
- ✅ RESTful APIs
- ✅ Gradle Wrapper (No need to install Gradle)

---

## 💻 Technologies Used

- Java 17+
- Spring Boot
- Spring Web, Spring Data JPA
- H2 Database (In-Memory)
- Gradle Wrapper

---

## 📁 Project Structure


train-ticket-system/
├── build.gradle
├── gradlew / gradlew.bat
├── settings.gradle
├── src/
│   └── main/
│       ├── java/com/example/trainsystem/
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   ├── model/
│       │   └── TrainsystemApplication.java
│       └── resources/
│           ├── application.properties


---

## 🚀 Getting Started

### 🧰 Prerequisites
- Java 17+ installed
- No need for Gradle – uses the wrapper (gradlew)

---

### ⚙ Run the Project

> 💡 Run these commands from the root Train-Ticket-Reservation-System-master/ directory

Commands:
bash
./gradlew clean build
./gradlew bootRun

---

## 🗄 H2 Database Configuration

Access the in-memory H2 database console:

- 🌐 URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: jdbc:h2:mem:trainticketsystem
- Username: sa
- Password: (leave blank)

### application.properties
properties
spring.datasource.url=jdbc:h2:mem:trainsystemdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
........
........


---

## 🔧 Troubleshooting

- *404 Whitelabel Error* → You may be accessing / instead of a valid endpoint.
- *Port Conflict* → Change port in application.properties:
  properties
  server.port=8081
  

---
