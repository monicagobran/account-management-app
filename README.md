# Account Management API
#### This is an account management application built with Spring Boot. It allows users to perform various operations related to managing accounts, such as opening accounts, depositing funds, withdrawing funds, and checking balance.

  ## Features
- **Open Account:** User can open a new account, which generates a unique account ID.
- **Deposit Funds:** User can deposit funds into their account.
- **Withdraw Funds:** User can withdraw funds from their account.
- **Check Balance:** User can check the balance of their account.


## Technologies Used

- Java Spring Boot Framework
- Swagger for API documentation
- JUnit and Mockito for unit testing
- Maven for project management
- Postman for API testing
  
## Getting Started
#### To get started with the application, follow these steps:

1. Clone the repository: `git clone https://github.com/monicagobran/account-management-app.git`
2. Navigate to the project folder: `cd account-management-app`
3. Build and run the project: `mvn spring-boot:run`
4. Once the application is running, you can access the API documentation using Swagger UI at `http://localhost:8080/swagger-ui.html`.

#### To run the application through a docker container, follow these steps:

1. Clone the repository: `git clone https://github.com/monicagobran/account-management-app.git`
2. Navigate to the project folder: `cd account-management-app`
3. Build a docker image: `docker build -t account-management-app .`
4. Run the docker container `docker run -p 8080:8080 account-management-app`
5. Once the container is running, you can access the API documentation using Swagger UI at `http://localhost:8080/swagger-ui.html`


## API Endpoints
- **POST /account:** Open a new account.
- **POST /account/{accountId}/deposit:** Deposit funds into an account.
- **POST /account/{accountId}/withdraw:** Withdraw funds from an account.
- **GET /account/{accountId}/balance:** Check the balance of an account.

## Future Considerations
- **Persistent Database:** Consideration of switching to a more robust and scalable database solution for production environments, such as PostgreSQL or MySQL.
- **Security:** Implementation of authentication and authorization mechanisms to secure API endpoints and protect sensitive data.