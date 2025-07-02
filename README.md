# Toothline Dental Backend

A Spring Boot backend for **Toothline Dental**, providing RESTful APIs, JWT-based authentication, and PostgreSQL integration.

---

## 🚀 Features

- ✅ RESTful API using Spring Web
- ✅ PostgreSQL with Spring Data JPA
- ✅ Input validation with Jakarta Validation
- ✅ Hot reload via Spring Boot DevTools
- ✅ JWT-based authentication
- 🤖 AI chatbot integration (planned)
- 🐘 Flyway-ready database migration (optional)

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **Framework:** Spring Boot 3.2+
- **Database:** PostgreSQL
- **Authentication:** Spring Security with planned JWT support
- **AI Integration:** (e.g. OpenAI API or custom chatbot backend)
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

- AI chatbot integration (e.g. patient inquiries or appointment handling)
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
