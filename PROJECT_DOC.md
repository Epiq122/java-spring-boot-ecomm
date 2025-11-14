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
- **Spring Data JPA** - Database persistence and ORM
- **Hibernate** - JPA implementation and ORM framework
- **H2 Database** - In-memory relational database for development
- **Maven** - Dependency management and build tool

### Planned Technologies

- Spring Security - Authentication and authorization
- JWT - Token-based authentication
- Lombok - Reduce boilerplate code
- ModelMapper/MapStruct - DTO mapping
- Bean Validation - Request validation

---

## 📋 Features

### Implemented

- ✅ Project initialization with Spring Boot 3.5.7
- ✅ Maven project structure
- ✅ Basic REST API setup
- ✅ **Database Integration with Spring Data JPA**
    - H2 in-memory database configuration
    - Hibernate ORM integration
    - JpaRepository pattern for data access
    - Entity mapping with JPA annotations
    - Auto-generated primary keys (IDENTITY strategy)
    - H2 console enabled for database inspection
- ✅ **Category Management API (CRUD with Database Persistence)**
    - GET `/api/public/categories` - Retrieve all categories
    - POST `/api/public/categories` - Create new category
    - PUT `/api/public/categories/{categoryId}` - Update existing category
    - DELETE `/api/admin/categories/{categoryId}` - Delete category
    - Database persistence with H2
    - Exception handling for not found resources

### Planned Features

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

### Category Management (Database Persistence with JPA)

**Files:**

- [`CategoryController.java`](src/main/java/ca/robertgleason/project/controller/CategoryController.java) - REST
  endpoints
- [`CategoryService.java`](src/main/java/ca/robertgleason/project/service/CategoryService.java) - Service interface
- [`CategoryServiceImpl.java`](src/main/java/ca/robertgleason/project/service/CategoryServiceImpl.java) - Service
  implementation
- [`CategoryRepository.java`](src/main/java/ca/robertgleason/project/repositories/CategoryRepository.java) - JPA
  repository interface
- [`Category.java`](src/main/java/ca/robertgleason/project/model/Category.java) - JPA entity

**Technical Decisions:**

1. **JpaRepository Pattern**: Extended `JpaRepository<Category, Long>` for automatic CRUD implementation - Spring
   provides all basic operations without custom code
2. **Entity Mapping**: Used `@Entity(name = "categories")` to map Category class to database table
3. **Auto-Generated IDs**: Configured `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-increment
   primary keys
4. **H2 In-Memory Database**: Using H2 for development - fast, embedded, no external setup required
5. **Service Interface Pattern**: Maintained interface/implementation separation for testability and flexibility
6. **Constructor Injection**: Used for CategoryRepository dependency - promotes immutability and easier testing
7. **Exception Handling**: ResponseStatusException for HTTP error responses with appropriate status codes

**Common Pitfalls Encountered & Solutions:**

1. **StaleObjectStateException Issue**: Initially attempted to manually set categoryId in createCategory method,
   conflicting with database auto-generation
    - **Solution**: Removed manual ID assignment - let JPA/Hibernate handle ID generation automatically
    - **Lesson**: Never manually set IDs when using @GeneratedValue

2. **Update Method Pattern**: Proper way to update entities with JPA
    - First verify entity exists with findById()
    - Set the ID on incoming entity to ensure update (not insert)
    - Call repository.save() which performs merge operation
    - **Pattern**: `category.setCategoryId(categoryId); savedCategory = repository.save(category);`

**Database Configuration:**

- H2 in-memory database configured in `application.properties`
- H2 console enabled at `/h2-console` for database inspection
- Hibernate auto-DDL set to `create-drop` for development (recreates schema on each restart)
- SQL logging enabled for debugging (`show-sql=true`, `format_sql=true`)
- Entity tables automatically created on application startup

**Gotchas & Notes:**

- JpaRepository provides methods out-of-the-box: save(), findAll(), findById(), delete(), etc.
- save() method performs both insert and update (insert if ID is null, update if ID exists)
- Always check entity existence before update/delete operations
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

