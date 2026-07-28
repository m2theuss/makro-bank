# Makro Bank

Makro Bank is a console-based banking application built with Java.

The project was designed to implement a complete banking CRUD application using only core Java technologies, focusing on object-oriented programming, layered architecture, data persistence with JDBC, and authentication without relying on backend frameworks.

## Features

- User registration
- User authentication
- Account management
- Balance inquiry
- Deposit
- Withdrawal
- Money transfer
- Password hashing with salt
- Token-based session authentication
- SQLite persistence

## Technologies

- Java 21
- Maven
- JDBC
- SQLite

## Project Structure

```
src
├── config
├── controller
├── dao
├── domain
├── security
├── util
└── view
```

## Architecture

The application follows a layered structure to separate responsibilities.

| Layer | Responsibility |
|-------|----------------|
| Controller | Handles application flow and user interactions |
| DAO | Database access using JDBC |
| Domain | Business entities |
| Security | Password hashing, salt generation, and token management |
| View | Console interface |
| Config / Util | Configuration and utility classes |

## Concepts Covered

- Object-Oriented Programming
- Layered Architecture
- JDBC
- SQLite
- Authentication
- Password hashing with salt
- Session management
- Exception handling
- Maven dependency management

## Requirements

- Java 21
- Maven 3.9 or newer

## Running

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project directory:

```bash
cd makro-bank
```

Compile and install the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn exec:java
```

The project can be started directly with `mvn exec:java` because the entry point is configured in the project's `pom.xml` through the Maven Exec Plugin. No additional configuration or manual class selection is required.

## Purpose

The goal of this project is to demonstrate how a complete banking application can be built using only core Java, JDBC, and SQLite.

Instead of relying on frameworks, the project focuses on implementing the application's core components manually, including persistence, authentication, layered architecture, and business logic. This approach provides a deeper understanding of how these mechanisms work before introducing higher-level frameworks.
