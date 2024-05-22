# Online Book Store API

## Overview

The Online Book Store API is a RESTful service for managing an online bookstore. It provides endpoints for managing users, books, orders, and reviews. The API is built using Spring Boot and follows a layered architecture with controllers, services, and repositories.

## Features

- User registration and authentication (JWT-based)
- CRUD operations for books, orders, and reviews
- Integration with PostgreSQL for data persistence
- API documentation using Swagger (SpringDoc)

## Technologies Used

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- H2 Database (for testing)
- JWT (JSON Web Tokens)
- Swagger (SpringDoc)
- JUnit 5
- Mockito

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL

### Setup

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/online-book-store-api.git
    cd online-book-store-api
    ```

2. **Configure PostgreSQL**:
    Create a PostgreSQL database and update the `application.properties` file with your database credentials:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build the project**:
    ```bash
    mvn clean install
    ```

4. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

5. **Access Swagger UI**:
    Open your browser and navigate to `http://localhost:8080/swagger-ui.html` to explore the API documentation.

## API Endpoints

### User Endpoints

- **POST /api/users/register**: Register a new user
- **POST /api/users/login**: Authenticate a user and return a JWT token

### Book Endpoints

- **GET /api/books**: Get all books
- **GET /api/books/{id}**: Get a book by ID
- **POST /api/books**: Add a new book (Admin only)
- **PUT /api/books/{id}**: Update a book (Admin only)
- **DELETE /api/books/{id}**: Delete a book (Admin only)

### Order Endpoints

- **POST /api/orders**: Create a new order
- **GET /api/orders/{id}**: Get an order by ID

### Review Endpoints

- **POST /api/reviews**: Add a new review
- **GET /api/reviews/books/{bookId}**: Get reviews for a specific book

## Running Tests

The project includes unit and integration tests. To run the tests, use the following command:

```bash
mvn test
