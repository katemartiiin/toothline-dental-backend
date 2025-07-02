# Toothline Dental Clinic System

A Spring Boot application for **Toothline Dental Clinic**, providing RESTful APIs, JWT-based authentication, and PostgreSQL integration.

---

## 🦷 About the Name
Toothline is a name derived from two words:
- Tooth – representing the dental domain
- Baseline – representing a base template or starting point

Together, Toothline reflects the project's purpose as a foundational template for building dental clinic systems.

## 🚀 Features

- ✅ RESTful API using Spring Web
- ✅ PostgreSQL with Spring Data JPA
- ✅ Input validation with Jakarta Validation
- ✅ Hot reload via Spring Boot DevTools
- ✅ JWT-based authentication

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **Framework:** Spring Boot 3.2+
- **Database:** PostgreSQL
- **Authentication:** Spring Security with JWT support
- **Build Tool:** Gradle (Kotlin DSL)
- **Java Version:** 21

---

## 📦 Project Structure

```
src/
├── main/
│   ├── kotlin/com/toothline/dental
│   │   ├── common/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── security/
│   │   ├── service/
│   │   └── system/
│   └── resources/
│       ├── application.properties
│       └── ...
└── test/
```

---

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/katemartiiin/toothline-dental-backend.git
cd toothline-dental-backend
```

### 2. Configure Environment

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/toothlinedb
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run the App

```bash
./gradlew bootRun
```

---

## 🧪 Running Tests

```bash
./gradlew test
```

---

## 🔐 Future Features

- Email/SMS notification support

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

---

## 📄 License

This project is licensed under the [Apache 2.0 License](LICENSE).

---

## 🧠 Author

Developed by **Kate Janeen Martin.**  
👩‍💻 [@katemartiiin](https://github.com/katemartiiin)
