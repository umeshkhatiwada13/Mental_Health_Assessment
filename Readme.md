# Spring Boot User Registration & Authentication with JWT

## Project Overview
This is a Spring Boot application that provides user registration and login functionalities, with JWT (JSON Web Token)-based authentication. It allows users to register with their email and password, and returns a JWT token upon successful login. The application ensures that an email cannot be used more than once for registration.

## Features
- **User Registration:** Allows new users to register with email and password.
- **JWT Authentication:** After logging in, users will receive a JWT token for authentication.
- **Admin User:** A default admin user is created when the application runs for the first time.
- **Email Uniqueness:** The application prevents duplicate email registrations.

## Setup Instructions

### Prerequisites
- JDK 11 or higher
- Maven
- PostgreSQL

### Installation

1. **Clone the Repository:**
   Clone this repository to your local machine:
   ```bash
   git clone https://github.com/umeshkhatiwada13/Mental_Health_Assessment
   cd Mental_Health_Assessment

Configure the Database: Edit src/main/resources/application.properties to configure your database connection.
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Install Dependencies: Install project dependencies with Maven:

    mvn clean install

Run the Application: Start the Spring Boot application:

    mvn spring-boot:run

Endpoints Description

    POST /register

        Description: Registers a new user with the provided email and password.

        Request Body:

{
"email": "user@example.com",
"password": "password123"
}

Response:

    {
      "message": "User registered successfully",
      "statusCode": 200
    }

POST /login

    Description: Authenticates the user and returns a JWT token.

    Request Body:

{
"email": "user@example.com",
"password": "password123"
}

Response:

        {
          "message": "Login successful",
          "statusCode": 200,
          "authToken": "your-jwt-token"
        }

JWT Usage

    Authentication: After login, the server returns a JWT token.

    Include JWT in Requests: For protected routes, include the JWT token in the Authorization header:

Authorization: Bearer your-jwt-token

Token Expiration: JWT tokens are valid for a limited period. If the token expires, the user will need to log in again to get a new token.

### How to Run the Project

Run the application using Maven:

    mvn spring-boot:run

### Testing: Use Postman or cURL to test the /register and /login endpoints:

Send a POST request to /register to register a new user.

Send a POST request to /login to authenticate and get a JWT token.