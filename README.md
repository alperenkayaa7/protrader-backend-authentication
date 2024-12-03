# ProTrader Authentication Service

## Overview
The ProTrader Authentication Service is a key component of the ProTrader platform, designed to manage user authentication and authorization securely and efficiently. Built with modern backend technologies, this service ensures secure login, registration, and token-based authorization.

## Features
- **User Authentication**: Secure login and registration using Spring Boot and PostgreSQL.
- **JWT Token Support**: Stateless authentication using JSON Web Tokens.
- **Role-Based Access Control**: Manage user permissions and roles.
- **Extensible Architecture**: Designed for easy integration with other microservices.
- **Dockerized Deployment**: Ready-to-deploy with a Dockerfile.

## Technologies Used
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Token)
- **Build Tool**: Maven
- **Deployment**: Docker, GitLab CI/CD

## Prerequisites
To run this project locally, you need:
- JDK 17+
- Maven
- Docker
- PostgreSQL

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/your-username/protrader-authentication-service.git
cd protrader-authentication-service
```

### Configure the Database
1. Create a PostgreSQL database.
2. Update the `application.yml` file with your database credentials:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/protrader_auth
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: update
   ```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080`.

## API Documentation
The API is documented using Swagger. Access the documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Example Endpoints
#### User Registration
**POST** `/api/auth/register`
```json
{
  "username": "user",
  "password": "securePassword",
  "email": "user@example.com"
}
```

#### User Login
**POST** `/api/auth/login`
```json
{
  "username": "user",
  "password": "securePassword"
}
```

## Running with Docker
1. Build the Docker image:
   ```bash
   docker build -t protrader-auth-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8080:8080 protrader-auth-service
   ```

## Testing
Run the test suite using Maven:
```bash
mvn test
```

## Continuous Integration
The project includes a `.gitlab-ci.yml` file for CI/CD pipeline:
- Builds the project.
- Runs tests.
- Deploys the Docker container.

## Contribution
Contributions are welcome! Please see the `CONTRIBUTING.md` file for guidelines.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact
For any inquiries, please contact [your-email@example.com].
