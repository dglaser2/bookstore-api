# Online Book Store API

## Overview

The Online Book Store API is a RESTful service for managing an online bookstore. It provides endpoints for managing users, books, orders, and reviews. The API is built using Spring Boot and follows a layered architecture with controllers, services, and repositories. It was initialized with [start.spring.io](start.spring.io).

## Features

- User registration and authentication (JWT-based)
- CRUD operations for books, orders, and reviews
- Integration with PostgreSQL for data persistence
- API documentation using Swagger (SpringDoc)

## Technologies Used

- Java 17
- Spring Boot 3.2.5
- PostgreSQL
- JWT (JSON Web Tokens)
- JUnit 5
- Mockito
- Spring Security
- Java Database Connectivity (JDBC)
- H2 Database (for testing)
- Swagger (SpringDoc)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL

### Setup

1. **Clone the repository**:
    ```bash
    git clone https://github.com/dglaser2/bookstore-api.git
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

- **GET /api/books**: Get all books in a paginated list
- **GET /api/books/{id}**: Get a book by ID
- **GET /api/books/search**: Search for book based on title, author, or description

### Order Endpoints

- **POST /api/orders**: Create a new order
- **GET /api/orders/{id}**: Get an order by ID

### Review Endpoints

- **POST /api/reviews**: Add a new review
- **GET /api/reviews/books/{bookId}**: Get reviews for a specific book

## Project Architecture

The project follows a layered architecture to separate concerns and improve maintainability. In some of these layers, interfaces are implemented by classes ending with Impl, such as UserServiceImpl, or BookRepositoryImpl. These implementation classes contain the actual business logic and call methods specified in the interfaces to perform operations.

### Domain Layer
The domain layer contains the core entities of the application. These are the classes that represent the data and business rules of the application.

- Entities: Classes such as User, Book, Order, and Review that map to database tables.
### Repository Layer
The repository layer is responsible for data access and persistence. It interacts with the database using the JDBC interface.

- Repositories: Interfaces like UserRepository, BookRepository, OrderRepository, and ReviewRepository that provide CRUD operations.
### Service Layer
The service layer contains the business logic of the application. It interacts with the repository layer to perform operations and handle business rules.

- Services: Classes such as UserService, BookService, OrderService, and ReviewService that contain methods to perform business operations.
### Resource Layer (Controller Layer)
The resource layer (also known as the controller layer) handles HTTP requests and responses. It exposes endpoints for the API and interacts with the service layer.

- Controllers: Classes such as UserResource, BookResource, OrderResource, and ReviewResource that map HTTP requests to service methods.
### Security Layer
The security layer handles authentication and authorization using Spring Security and JWT.

- Security Configurations: Classes such as AuthFilter that define security rules and JWT token handling.

## Running Tests

The project includes unit and integration tests. To run the tests, use the following command:

```bash
mvn test
