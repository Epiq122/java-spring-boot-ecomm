# Spring Boot E-Commerce Application

A full-stack e-commerce application built with Spring Boot 3.5.7 and Java 21. This project demonstrates modern backend
development practices, RESTful API design, and enterprise-level application architecture.

---

## 🚀 Project Overview

This is a production-ready e-commerce backend system showcasing real-world Spring Boot development patterns and best
practices.

**Status:** In Development  
**Started:** November 12, 2025

---

## 🛠️ Tech Stack

### Backend

- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.5.7** - Core framework
- **Spring Web** - RESTful API development
- **Maven** - Dependency management and build tool

### Planned Technologies

- Spring Data JPA - Database persistence layer
- Spring Security - Authentication and authorization
- MySQL/PostgreSQL - Relational database
- JWT - Token-based authentication
- Lombok - Reduce boilerplate code
- ModelMapper/MapStruct - DTO mapping

---

## 📋 Features

### Implemented

- ✅ Project initialization with Spring Boot 3.5.7
- ✅ Maven project structure
- ✅ Basic REST API setup
- ✅ **Category Management API (CRUD)**
    - GET `/api/public/categories` - Retrieve all categories
    - POST `/api/public/categories` - Create new category
    - PUT `/api/public/categories/{categoryID}` - Update existing category
    - DELETE `/api/admin/categories/{categoryID}` - Delete category
    - In-memory data storage with auto-incrementing IDs
    - Exception handling for not found resources

### Planned Features

- Database persistence with Spring Data JPA
- User authentication and authorization (JWT)
- Product catalog management (CRUD operations)
- Shopping cart functionality
- Order processing system
- Payment integration
- User profile management
- Admin dashboard
- Product search and filtering
- Pagination and sorting
- File upload (product images)
- Email notifications

---

## 🏗️ Architecture & Design Patterns

### Architectural Approach

- **Layered Architecture**: Controller → Service → Repository
- **RESTful API Design**: Following REST principles and HTTP standards
- **DTO Pattern**: Separation between entities and API contracts (planned)
- **Dependency Injection**: Leveraging Spring's IoC container
- **Service Interface Pattern**: Interface/Implementation separation for flexibility and testability

### Backend Best Practices Implemented

- **Constructor-based Dependency Injection**: Using constructor injection for required dependencies (better than field
  injection)
- **RESTful Endpoint Naming**: Clear, resource-based URL structure (`/api/public/categories`)
- **Proper HTTP Status Codes**: 200 OK, 201 Created, 404 Not Found
- **Exception Handling**: Using Spring's ResponseStatusException for error responses
- **Service Layer Abstraction**: Interface-based services for loose coupling

---

## 📁 Project Structure

```
sb-ecomm/
├── src/main/java/ca/robertgleason/project/
│   ├── SbEcommApplication.java      # Main application entry point
│   ├── controller/                   # REST API endpoints
│   ├── service/                      # Business logic layer
│   ├── repository/                   # Data access layer
│   ├── model/entity/                 # JPA entities
│   ├── model/dto/                    # Data Transfer Objects
│   ├── config/                       # Spring configuration classes
│   ├── security/                     # Security configuration & filters
│   └── exception/                    # Custom exceptions & handlers
├── src/main/resources/
│   ├── application.properties        # Application configuration
│   └── static/                       # Static resources
└── src/test/                         # Unit and integration tests
```

---

## 🔑 Key Implementation Notes

### Category Management (In-Memory Implementation)

**Files:**

- [`CategoryController.java`](src/main/java/ca/robertgleason/project/controller/CategoryController.java) - REST
  endpoints
- [`CategoryService.java`](src/main/java/ca/robertgleason/project/service/CategoryService.java) - Service interface
- [`CategoryServiceImpl.java`](src/main/java/ca/robertgleason/project/service/CategoryServiceImpl.java) - Service
  implementation
- [`Category.java`](src/main/java/ca/robertgleason/project/model/Category.java) - Domain model

**Technical Decisions:**

1. **In-Memory Storage**: Using ArrayList for temporary data storage (will be replaced with JPA repository later)
2. **Auto-incrementing IDs**: Manual ID generation with Long counter (database will handle this in production)
3. **Service Interface Pattern**: Separate interface and implementation for better testability and future flexibility
4. **Constructor Injection**: Used for CategoryService dependency - promotes immutability and easier testing
5. **Exception Handling**: ResponseStatusException for HTTP error responses with appropriate status codes
6. **Path Variable Naming**: Using `categoryID` consistently across endpoints for clarity

**Gotchas & Notes:**

- Current implementation is stateless - data resets on application restart
- Update endpoint doesn't return the updated category in response body (could be improved)
- No validation on incoming request data yet (to be added with Bean Validation)
- Public vs Admin endpoint differentiation established (`/public/` vs `/admin/`)

*This section will be expanded as more features are implemented*
progresses.*

---

## 🎯 Learning Outcomes

- Building enterprise-grade RESTful APIs with Spring Boot
- Implementing security with JWT authentication
- Database design and JPA relationships
- Exception handling and validation
- Testing strategies for Spring applications
- API documentation with Swagger/OpenAPI
- Deployment best practices

---

## 📝 API Documentation

*API endpoints will be documented here as they are implemented*

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Running the Application

```bash
# Clone the repository
git clone [repository-url]

# Navigate to project directory
cd sb-ecomm

# Run the application
./mvnw spring-boot:run

# Or build and run
./mvnw clean install
java -jar target/sb-ecomm-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

---

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Framework Reference](https://docs.spring.io/spring-framework/reference/)
- Course materials and notes (private)

---

## 👨‍💻 Developer

**Robert Gleason**  
Building this project as part of my full-stack development journey.

---

*Last Updated: November 12, 2025*

