# Coding Guidelines & Reusable Patterns

This document captures coding standards, design patterns, and reusable components developed throughout this project.
These patterns and utilities can be applied to future Spring Boot projects with minimal modification.

---

## 📐 Coding Standards

### General Principles

- Follow **SOLID** principles
- Write clean, self-documenting code
- Prefer composition over inheritance
- Keep methods focused and small (Single Responsibility)
- Use meaningful variable and method names

### Java Conventions

- **Package naming**: Lowercase, reverse domain notation (`ca.robertgleason.project`)
- **Class naming**: PascalCase (e.g., `UserService`, `ProductController`)
- **Method naming**: camelCase, verb-based (e.g., `getUserById`, `createOrder`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_ATTEMPTS`)
- **Variables**: camelCase, descriptive (e.g., `userId`, `productList`)

### Spring Boot Best Practices

- Use constructor injection over field injection
- Leverage `@RequiredArgsConstructor` (Lombok) for cleaner dependency injection
- Keep controllers thin - business logic belongs in services
- Use DTOs for API requests/responses (never expose entities directly)
- Implement proper exception handling with `@ControllerAdvice`

### RESTful API Conventions ✅ *Established*

**Endpoint Structure:**

```
/api/{access-level}/{resource-name}/{optional-id}
```

**Examples:**

- `GET /api/public/categories` - Public access, list all
- `POST /api/public/categories` - Public access, create
- `PUT /api/public/categories/{id}` - Public access, update
- `DELETE /api/admin/categories/{id}` - Admin only, delete

**HTTP Method Usage:**

- **GET** - Retrieve resources (safe, idempotent)
- **POST** - Create new resources
- **PUT** - Update existing resources (idempotent)
- **DELETE** - Remove resources (idempotent)

**Status Code Standards:**

- **200 OK** - Successful GET, PUT, DELETE
- **201 Created** - Successful POST
- **404 Not Found** - Resource doesn't exist
- **400 Bad Request** - Validation errors (future)
- **401 Unauthorized** - Authentication required (future)
- **403 Forbidden** - Insufficient permissions (future)

---

## 🏗️ Architectural Patterns

### Layered Architecture

```
Controller Layer (REST endpoints)
    ↓
Service Layer (Business logic)
    ↓
Repository Layer (Data access)
```

**Key Rules:**

- Controllers handle HTTP concerns only
- Services contain business logic and orchestration
- Repositories interact with the database
- Never skip layers (e.g., Controller → Repository directly)

### DTO Pattern

- **Request DTOs**: For incoming data (validation, transformation)
- **Response DTOs**: For outgoing data (selective field exposure)
- **Benefits**: Decoupling, security, API versioning flexibility

---

## 🔄 Reusable Design Patterns

### Service Interface Pattern ✅ *Implemented*

**Purpose**: Decouple service contracts from implementations for flexibility and testability.

**Pattern:**

```java
// Interface defines the contract
public interface EntityService {
    List<Entity> getAllEntities();

    void createEntity(Entity entity);

    Entity updateEntity(Entity entity, Long id);

    String deleteEntity(Long id);
}

// Implementation provides the logic
@Service
public class EntityServiceImpl implements EntityService {
    // Implementation details
}
```

**Benefits:**

- Easy to mock in unit tests
- Supports multiple implementations (e.g., InMemory vs Database)
- Clear contract definition
- Follows Dependency Inversion Principle

**When to Use:** For all service layer components in the application.

---

### Patterns to Implement:

- Builder Pattern (for complex object creation)
- Factory Pattern (for object instantiation logic)
- Strategy Pattern (for interchangeable algorithms)
- Repository Pattern (data access abstraction)
- Specification Pattern (dynamic queries)

---

## 🛠️ Reusable Utility Classes & Components

*Templates and utilities that can be copied to other projects with minor tweaking.*

### REST Controller Template ✅ *Implemented*

**Purpose**: Standard template for creating RESTful CRUD controllers.

**Template:**

```java

@RestController
@RequestMapping("/api")
public class EntityController {

    private final EntityService entityService;

    // Constructor injection (preferred over field injection)
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/public/entities")
    public ResponseEntity<List<Entity>> getAllEntities() {
        List<Entity> entities = entityService.getAllEntities();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PostMapping("/public/entities")
    public ResponseEntity<String> createEntity(@RequestBody Entity entity) {
        entityService.createEntity(entity);
        return new ResponseEntity<>("Entity added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/public/entities/{id}")
    public ResponseEntity<String> updateEntity(@RequestBody Entity entity, @PathVariable Long id) {
        try {
            Entity updated = entityService.updateEntity(entity, id);
            return new ResponseEntity<>("Entity updated successfully", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/admin/entities/{id}")
    public ResponseEntity<String> deleteEntity(@PathVariable Long id) {
        try {
            String status = entityService.deleteEntity(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
```

**Customization Points:**

- Replace `Entity` with your model class name
- Adjust endpoint paths (`/public/entities` → `/public/your-resource`)
- Modify response types (String vs DTO objects)
- Add validation annotations (@Valid, @NotNull, etc.)

**Best Practices Applied:**

- Constructor injection for dependencies
- Consistent HTTP status codes (200 OK, 201 Created, 404 Not Found)
- Try-catch for exception handling
- Clear endpoint naming conventions

---

### In-Memory Service Implementation Template ✅ *Implemented*

**Purpose**: Simple in-memory CRUD service for prototyping before database integration.

**Template:**

```java

@Service
public class EntityServiceImpl implements EntityService {

    private final List<Entity> entities = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Entity> getAllEntities() {
        return entities;
    }

    @Override
    public void createEntity(Entity entity) {
        entity.setId(nextId++);
        entities.add(entity);
    }

    @Override
    public Entity updateEntity(Entity entity, Long id) {
        Optional<Entity> optional = entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (optional.isPresent()) {
            Entity existing = optional.get();
            // Update fields as needed
            existing.setName(entity.getName());
            return existing;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }

    @Override
    public String deleteEntity(Long id) {
        Entity entity = entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

        entities.remove(entity);
        return "Entity with id: " + id + " deleted successfully";
    }
}
```

**When to Use:**

- Early prototyping and API testing
- Learning/demonstration purposes
- Quick MVP without database setup

**Migration Path:** Replace with JPA repository implementation once database is configured.

---

### Generic Response Wrapper

*To be implemented*

### Custom Exception Classes

*To be implemented*

### Pagination Utility

*To be implemented*

### Validation Utilities

*To be implemented*

### Date/Time Utilities

*To be implemented*

---

## 📋 File Templates

### Standard Controller Template

```java

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService service;

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> create(@Valid @RequestBody ResourceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Standard Service Template

```java

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public List<ResourceDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ResourceDTO findById(Long id) {
        Resource resource = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toDTO(resource);
    }

    public ResourceDTO create(ResourceRequestDTO dto) {
        Resource resource = mapper.toEntity(dto);
        Resource saved = repository.save(resource);
        return mapper.toDTO(saved);
    }

    public ResourceDTO update(Long id, ResourceRequestDTO dto) {
        Resource existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        mapper.updateEntity(dto, existing);
        Resource updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
```

### Standard Repository Template

```java

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    // Custom query methods as needed
}
```

### Standard Entity Template

```java

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
```

---

## 🔐 Security Patterns

*Security implementations and patterns will be documented here as they are implemented.*

---

## 🧪 Testing Patterns

*Testing strategies and patterns will be documented here as they are implemented.*

### Unit Test Template

```java

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository repository;

    @Mock
    private ResourceMapper mapper;

    @InjectMocks
    private ResourceService service;

    @Test
    void findById_WhenResourceExists_ReturnsDTO() {
        // Arrange

        // Act

        // Assert
    }
}
```

---

## 📦 Dependency Management

### Essential Dependencies

```xml
<!-- To be populated as dependencies are added -->
```

### Recommended Versions

*Will be maintained as the project evolves*

---

## 🎯 API Design Guidelines

### RESTful Conventions

- **GET**: Retrieve resources (idempotent)
- **POST**: Create new resources
- **PUT**: Update entire resource
- **PATCH**: Partial update
- **DELETE**: Remove resource (idempotent)

### URL Structure

```
/api/v1/{resource}           - Collection endpoint
/api/v1/{resource}/{id}      - Single resource endpoint
/api/v1/{resource}/{id}/{sub-resource} - Nested resource
```

### Response Status Codes

- **200 OK**: Successful GET, PUT, PATCH
- **201 Created**: Successful POST
- **204 No Content**: Successful DELETE
- **400 Bad Request**: Validation error
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource doesn't exist
- **500 Internal Server Error**: Server error

### Response Format

```json
{
  "data": {},
  "message": "Success",
  "timestamp": "2025-11-12T10:30:00"
}
```

---

## 💡 Lessons Learned & Best Practices

*This section will capture important insights, gotchas, and best practices discovered during development.*

---

## 🔧 Configuration Patterns

### application.properties Template

```properties
# Server Configuration
server.port=8080
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/dbname
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
# Logging
logging.level.root=INFO
logging.level.ca.robertgleason.project=DEBUG
```

---

## 📚 Useful Resources & References

- [Spring Boot Best Practices](https://docs.spring.io/spring-boot/reference/)
- [REST API Design Guidelines](https://restfulapi.net/)
- [Effective Java (Joshua Bloch)](https://www.oreilly.com/library/view/effective-java/9780134686097/)

---

## 🧭 Project-wide Conventions (Apply Across All Future Projects)

### ✅ Commit Message Conventions

Use concise, professional messages. Prefer Conventional Commits style:

```
feat: add product pagination API
fix: correct null pointer in order service
refactor: extract payment strategy interface
docs: update API usage examples
test: add unit tests for cart calculations
chore: bump spring boot version to 3.5.7
perf: optimize repository query for product search
sec: add JWT token expiration validation
```

Rules:

- Imperative mood ("add" not "added")
- First line ≤ 72 chars
- Optional body: what & why (avoid how unless complex)
- Reference ticket/issue ID if applicable (e.g., PROJ-123)

### 🌿 Branching Strategy (Git Flow Lite)

- main: Always deployable / stable
- develop (optional): Integration branch (skip if small project)
- feature/<short-kebab-description>
- fix/<bug-summary>
- perf/<optimization>
- release/x.y.z (optional for formal releases)
  Merge Policy:
- No direct commits to main
- Features merged via Pull Request with review & CI passing

### 👀 Pull Request Template (Recommended)

Include in repo as PULL_REQUEST_TEMPLATE.md:

```
## Summary
(What does this PR do?)

## Motivation / Context
(Why is it needed?)

## Changes
- List key changes

## Screenshots / Evidence (if UI/API contract)

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual smoke test performed

## Checklist
- [ ] Follows coding guidelines
- [ ] No secrets committed
- [ ] Docs updated (PROJECT_DOC.md / API docs)
- [ ] No failing CI jobs
```

### 🧪 Testing Strategy (Pyramid)

```
      UI / E2E (few, critical flows)
   Integration (services, repos, security)
Unit (fast, isolated, majority)
```

Guidelines:

- Unit tests: business logic, edge cases, pure functions
- Integration tests: database interactions, security filters, serialization
- Avoid overusing E2E – keep deterministic and minimal
- Use Testcontainers for DB-dependent tests when possible

### 🔐 Security Checklist

- [ ] Use HTTPS in production
- [ ] Store passwords with strong hashing (BCrypt/Argon2)
- [ ] JWT tokens: signed, short-lived access + refresh strategy
- [ ] Input validation (Bean Validation + manual checks)
- [ ] Centralized exception handling hides internals
- [ ] Principle of Least Privilege (roles/authorities)
- [ ] Avoid leaking stack traces to clients
- [ ] Use parameterized queries (JPA handles this) – beware native queries
- [ ] CORS configured explicitly
- [ ] Sensitive config via environment variables / secrets manager
- [ ] Audit logging for security-relevant actions

### ⚡ Performance Checklist

- [ ] Use pagination for list endpoints
- [ ] Avoid N+1 queries (use fetch joins / EntityGraph)
- [ ] Cache read-heavy endpoints (Spring Cache / Redis)
- [ ] Index DB columns used in filtering
- [ ] Profile slow queries (Hibernate statistics, logs)
- [ ] Bulk operations use batch processing settings
- [ ] Minimize DTO mapping overhead (MapStruct preferred)

### 📊 Observability & Monitoring

- Structured logging (JSON in production)
- Log correlation IDs (MDC + filter) for tracing requests
- Expose health & metrics endpoints (Spring Boot Actuator)
- Track: request latency, error rate, DB connection pool usage
- Add tracing (OpenTelemetry) if distributed

### 📄 Documentation Standards

- PROJECT_DOC.md: Keep features + architecture updated per feature merge
- PROGRESS.md: Chronological learning/work log – date + summary
- CODING_GUIDELINES.md: Only append new patterns when solidified
- API Docs: Use SpringDoc OpenAPI or similar; version endpoints explicitly if breaking changes
- Include examples for complex payloads (errors, validation responses)

### 🔐 Environment & Secrets Management

Never commit secrets. Use one of:

- Local: .env file (excluded via .gitignore)
- Prod: Vault / AWS Secrets Manager / Kubernetes secrets
- Spring Boot: `SPRING_DATASOURCE_PASSWORD` env variables
  Principles:
- Config layered: application.yml -> application-<env>.yml -> env vars
- Feature toggles via config properties

### 🔄 Database Migration Strategy

Use Flyway or Liquibase early.

- Versioned migrations: V1__init.sql, V2__add_user_table.sql
- No manual schema changes outside migration tool
- Rollback plan for destructive changes
- Avoid irreversible operations without backups

### 📦 Dependency Management Policy

- Pin major versions (avoid floating latest)
- Review transitive deps for vulnerabilities (OWASP Dependency Check)
- Remove unused libraries regularly
- Prefer stable over beta releases in production

### 🧱 Reusable Module Candidates

Extractable into separate libs for future reuse:

- Common error handling module (exception hierarchy + handler)
- Security module (JWT auth filter, token provider, auth service)
- Core utilities (date/time, collection helpers, Response wrapper)
- Mapping module (MapStruct mappers + central config)
- Audit/logging module (MDC filters, structured log utility)

### ✅ Release Readiness Checklist

Before tagging a release:

- [ ] All tests green (unit, integration)
- [ ] Security and performance checklist pass
- [ ] API docs updated & versioned
- [ ] No TODOs in code for shipped scope
- [ ] Dependencies scanned for CVEs
- [ ] Observability dashboards reflect new metrics
- [ ] Migration scripts applied & tested on staging

### 🧠 Interview / Portfolio Highlights (What Employers Care About)

Emphasize:

- Clean separation of concerns (layered architecture)
- Security (JWT, role-based access control, validation)
- Performance (pagination, caching, query optimization)
- Robust error handling & clear API contracts
- Automated tests (coverage + meaningful cases)
- Documentation & maintainability (consistent patterns)
- Extensibility (pluggable strategies, modular design)

### 🗂️ Example Directory Layout (When Fully Built)

```
src/main/java/.../common/        # Shared utilities, constants
src/main/java/.../config/        # Spring @Configuration classes
src/main/java/.../security/      # Auth, filters, JWT, roles
src/main/java/.../featureX/      # Feature vertical (controller/service/repo/entity/dto)
src/main/java/.../exception/     # Custom exceptions + handler
src/main/java/.../mapping/       # MapStruct mappers
src/main/java/.../audit/         # Audit & logging helpers
```

Vertical slice structure (group by feature) is preferred after initial bootstrap.

### 🏷️ Naming Patterns Recap

- Entities: Noun singular (Order, UserAccount)
- Tables: snake_case plural (orders, user_accounts)
- DTOs: <Entity>Name + (Request|Response|DTO) suffix
- Repos: <Entity>NameRepository
- Services: <Entity>NameService / <Capability>Service
- Controllers: <Entity>NameController
- Config: <Domain>Config (e.g., SecurityConfig)

### 🔁 Evolution & Refactoring Rules

- Introduce abstraction only after 2–3 duplication instances
- Track technical debt items in issues
- Schedule regular dependency updates / cleanup cycles

---

*Last Updated: November 12, 2025*
