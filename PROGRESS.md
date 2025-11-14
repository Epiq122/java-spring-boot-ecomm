# Learning Progress Log

This file tracks my progress through the Spring Boot Full Stack project, documenting sections completed, time invested,
and key learnings.

---

## Progress Entries

### Project Initialization - November 12, 2025

**Time Spent:** Initial setup  
**Summary:** Created Spring Boot 3.5.7 project with Java 21. Set up basic project structure with Maven, configured
initial dependencies (Spring Web, Spring Boot Starter), and established the foundation for the e-commerce application.

---

### Category CRUD API Implementation - November 12, 2025

**Time Spent:** ~2-3 hours  
**Summary:** Implemented complete CRUD operations for Category management using in-memory storage. Built RESTful API
endpoints (GET, POST, PUT, DELETE) with proper HTTP status codes and error handling. Established the foundational
three-layer architecture pattern (Controller → Service → Model) that will be used throughout the application, with
service interface/implementation separation for flexibility.

**Key Learning Points:**

- RESTful API design with Spring Web annotations (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping)
- Constructor-based dependency injection for better testability
- Service layer abstraction with interface/implementation pattern
- Exception handling using ResponseStatusException
- HTTP status code management with ResponseEntity

---

### Database Integration with Spring Data JPA - November 13, 2025

**Time Spent:** ~2-3 hours  
**Summary:** Integrated MySQL database using Spring Data JPA and Hibernate. Migrated from in-memory storage to database
persistence with JpaRepository. Configured Category entity with @Entity annotation and auto-generated primary keys using
@GeneratedValue strategy. Learned to troubleshoot common JPA issues including StaleObjectStateException caused by manual
ID assignment conflicting with database auto-generation.

**Key Learning Points:**

- Spring Data JPA repository pattern extending JpaRepository<Entity, ID>
- Entity mapping with @Entity, @Id, and @GeneratedValue annotations
- Database auto-generation strategies (GenerationType.IDENTITY for MySQL)
- Proper entity lifecycle management - avoiding manual ID assignment for auto-generated fields
- JPA version control and optimistic locking concepts
- Hibernate ORM configuration and automatic DDL generation
- Database connection configuration via application.properties

---

*Note: This log will be updated as each section is completed.*


