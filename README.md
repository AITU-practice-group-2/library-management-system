# Library Management System
A web-based library management system built with Spring Boot and Thymeleaf. The application provides a simple interface for managing books, reservations and reviews in a library setting.

## Project run
### 1. Clone the repository
`git clone https://github.com/Omirkhon/library-management-system.git`

`cd library-management-system`

### 2. Build
`./mvnw clean install`

### 3. Run
`./mvnw spring-boot:run `

## Technologies Used
• Java 21

• Spring Boot 3.5.0

• Thymeleaf

• Maven

• Spring Data JPA

• Spring MVC

• Spring Security

• Lombok

• PostgreSQL

• MapStruct

## Project Structure
```
src
└── main
    ├── java
    │   └── com
    │       └── practice
    │           └── librarysystem
    │               ├── HomeController.java
    │               ├── LibrarySystemApplication.java
    │               ├── author
    │               │   ├── Author.java
    │               │   ├── AuthorController.java
    │               │   ├── AuthorDTO.java
    │               │   ├── AuthorMapper.java
    │               │   ├── AuthorRepository.java
    │               │   ├── AuthorService.java
    │               │   └── Impl
    │               │       └── AuthorServiceImpl.java
    │               ├── book
    │               │   ├── Book.java
    │               │   ├── BookController.java
    │               │   ├── BookMapper.java
    │               │   ├── BookRepository.java
    │               │   ├── BookService.java
    │               │   ├── BookViewController.java
    │               │   └── dto
    │               │       ├── BookFullResponse.java
    │               │       ├── BookShortResponse.java
    │               │       ├── NewBookRequest.java
    │               │       └── UpdateBookRequest.java
    │               ├── category
    │               │   ├── Category.java
    │               │   ├── CategoryController.java
    │               │   ├── CategoryDTO.java
    │               │   ├── CategoryMapper.java
    │               │   ├── CategoryRepository.java
    │               │   ├── CategoryService.java
    │               │   ├── CategoryViewController.java
    │               │   └── impl
    │               │       └── CategoryServiceImpl.java
    │               ├── exception
    │               │   ├── ApiError.java
    │               │   ├── DataConflictException.java
    │               │   ├── ErrorHandler.java
    │               │   ├── ForbiddenAccessException.java
    │               │   ├── NotFoundException.java
    │               │   └── ValidationException.java
    │               ├── reservation
    │               │   ├── CertificateController.java
    │               │   ├── Reservation.java
    │               │   ├── ReservationController.java
    │               │   ├── ReservationMapper.java
    │               │   ├── ReservationRepository.java
    │               │   ├── ReservationService.java
    │               │   ├── ReservationStatus.java
    │               │   └── dto
    │               │       ├── CreateReservationDTO.java
    │               │       └── ReservationDTO.java
    │               ├── review
    │               │   ├── Review.java
    │               │   ├── ReviewController.java
    │               │   ├── ReviewMapper.java
    │               │   ├── ReviewRepository.java
    │               │   ├── ReviewRequestDTO.java
    │               │   ├── ReviewResponseDTO.java
    │               │   └── ReviewService.java
    │               ├── security
    │               │   ├── LoginController.java
    │               │   ├── SecurityConfig.java
    │               │   └── UserDetailsServiceImpl.java
    │               ├── statistics
    │               │   ├── StatsController.java
    │               │   ├── book
    │               │   │   └── BookStatisticsService.java
    │               │   └── user
    │               │       ├── PopularityService.java
    │               │       ├── UserAuthor.java
    │               │       ├── UserAuthorRepository.java
    │               │       ├── UserCategory.java
    │               │       └── UserCategoryRepository.java
    │               ├── user
    │               │   ├── Role.java
    │               │   ├── User.java
    │               │   ├── UserController.java
    │               │   ├── UserDto.java
    │               │   ├── UserMapper.java
    │               │   ├── UserNewDto.java
    │               │   ├── UserRepository.java
    │               │   ├── UserService.java
    │               │   └── UserViewController.java
    │               └── util
    │               │   ├── PdfCertificateGenerator.java
    │               │   └── RequestConstants.java
    └── resources
        ├── static
        ├── templates
        │   ├── book-details.html
        │   ├── book-list.html
        │   ├── categories.html
        │   ├── index.html
        │   ├── login.html
        │   ├── profile.html
        │   └── register.html
        ├── application.properties
        └── schema.sql
```

## Example API Endpoints
Authorisation APIs:

` GET /login` - login to existing profile

` GET /register` - register page

` POST /register` - creates new profile (User)

Book APIs:

` GET /books` - retrieve a list of books with optional filters for search, author, and category, with pagination support.

` GET /books/{id}` - get detailed information about a specific book by ID. Tracks view statistics.

` POST /books` - create a new book. Requires author and category IDs.

` PATCH /books/{id}` - update book details by ID. Requires author and category IDs.

` DELETE /books/{id}` - delete a book by ID.

` GET /books/recommendations` - get personalized book recommendations for the authenticated user.

` GET /books/popular` - get a list of popular books with pagination support.

Reservation APIs:

` POST /reservations` - create a new reservation for a book. Validates if the user is allowed to reserve it.

` GET /reservations` - retrieve all reservations.

` GET /reservations/{id}` - get reservation details by reservation ID.

` GET /reservations/user/{userId}` - get all reservations made by a specific user.

` GET /reservations/user/{userId}/active` - get active (non-returned or non-cancelled) reservations for a user.

` GET /reservations/book/{bookId}` - get all reservations for a specific book.

` GET /reservations/status/{status}` - get reservations filtered by their status (e.g., ACTIVE, CANCELLED, RETURNED).

` PUT /reservations/{id}/return` - mark a reservation as returned.

` PUT /reservations/{id}/cancel` - cancel an existing reservation.

` PUT /reservations/{id}/extend?newDueDate=yyyy-MM-ddTHH:mm:ss` - extend the due date of a reservation.

` GET /reservations/overdue` - get all overdue reservations.

` GET /reservations/due-soon?days={days}` - get reservations that are due within the specified number of days (default is 7).

` PUT /reservations/mark-overdue` - (ADMIN) mark all overdue reservations as overdue in the system.

` PUT /reservations/{id}` - update a reservation with new information.

` DELETE /reservations/{id}` - delete a reservation by ID.

` GET /reservations/can-reserve?userId={id}&bookId={id}` - check if a user can make a reservation for a given book.

` GET /reservations/stats` - retrieve general reservation statistics.

Review APIs:

` GET /reviews` -get a paginated list of all reviews.

` GET /reviews/{id}` - retrieve a specific review by ID.

` POST /reviews` - create a new review for a book. Authenticated user is tracked.

` GET /reviews/book/{bookId}` - get all reviews associated with a specific book.

` PUT /reviews/{id}` - update a review. Popularity rating is updated based on new score.

` DELETE /reviews/{id}` - delete a review by ID.
