# Magic: The Gathering Commander API

## Overview
This application is an API built for Magic: The Gathering (MTG) fans, specifically focused on the Commander format. It allows users to search, filter, and explore the vast library of MTG cards, sourced directly from the Magic: The Gathering API. The goal is to provide an easy and efficient way to interact with card data, making it useful for developers and players alike.

## Features
- **Access to Commander Cards**: The API pulls data directly from the official Magic API, ensuring up-to-date card information.
- **Filtering and Search**: Search for cards based on various attributes such as name, mana cost, type, and colors, ideal for building or refining Commander decks.
- **Commander-Specific Cards**: The API focuses on cards that are most relevant to the Commander format, a popular multiplayer MTG mode.

## Data Source
All card data is extracted from the official Magic: The Gathering API:
 ```markdown
 https://api.magicthegathering.io/v1/cards
  ```
This ensures that the card database is constantly updated with the latest releases and rule changes.

## How to Use
Once the database is populated, you can explore the various endpoints to:
- Search cards by name, type, color, or mana cost.
- Retrieve details about individual cards, such as abilities, card types, and artwork.
- Filter cards specifically for Commander deck building.

## Future Enhancements
- **Commander-specific rules**: Tailor the API to validate cards and decks based on Commander format restrictions.
- **Deck building**: Provide endpoints for creating, saving, and sharing Commander decks.
- **Community features**: Allow users to rate or comment on cards for better deck-building recommendations.


## Technologies Used

- **Spring Boot Starter Data JPA**: For object-relational mapping and database operations.
- **Spring Boot Starter Security**: To secure the application with authentication and authorization.
- **Spring Boot Starter Validation**: To handle validation for incoming data.
- **Spring Boot Starter Web**: To build RESTful web services and APIs.
- **Liquibase**: For database versioning and migration.
- **Java JWT**: For implementing JSON Web Token (JWT)-based authentication.
- **RabbitMQ**: For queue management and asynchronous message processing.
- **Lombok**: To reduce boilerplate code through annotations like `@Getter`, `@Setter`, etc.
- **Spring Boot DevTools**: To enhance the development experience with hot reloads.
- **PostgreSQL**: The primary database used for persistence.
- **SpringDoc OpenAPI**: For generating API documentation with OpenAPI/Swagger.
- **H2 Database**: An in-memory database used for testing purposes.
- **JUnit**: To facilitate unit and integration testing.
- **Spring Boot Starter Test**: For testing support in Spring Boot applications.
- **Spring Security Test**: For testing security components and authentication flows.
- **Spring Boot Starter Data Redis**: To integrate Redis as a caching layer.

## RabbitMQ Integration
The application uses RabbitMQ to handle asynchronous operations for deck management, ensuring better performance and scalability.

### Queues
- add-cards-queue: Handles the operation of adding a specific card to a deck.
   Endpoint:
   ```markdown
   POST /deck/add-card/{deckId}/{cardId}
   ```
  This route enqueues the operation to the add-cards-queue, allowing it to be processed asynchronously.


- deck_import_queue: Processes deck imports from uploaded files.
  Endpoint:
   ```markdown
   POST /deck/import/{deckName}
   ```
  This route accepts a file, reads its contents, and sends the data to the deck_import_queue for processing on the other side of the queue.

These queues decouple time-consuming operations from the main application flow, improving responsiveness.
## Setup

### Prerequisites
- **JDK 17**
- **Docker and Docker Compose**
- **Maven or Gradle** (depending on your build system)

### Running the Application
1. Clone the repository.
2. Configure PostgreSQL connection in the `application.yml` or `application.properties`.
3. Use Docker Compose to run the application:
   ```base
   docker-compose up -d
   ```
4. Access the API at `http://localhost:8080`.

## How to Populate the Database
To seed the application’s card database with data from the Magic API, use the following command:
  ```markdown
  localhost:8080/card/seed
  ```
This command fetches all the relevant cards from the Magic API and stores them in the local database, making them available for querying through the application’s endpoints.


### Database Migration
Liquibase is used to manage database versioning and migrations. The migration scripts are located in the `src/main/resources/db/changelog` directory.

To apply migrations, ensure the Liquibase configuration is correctly set up in `application.yml`.

### API Documentation
API documentation is automatically generated using OpenAPI (Swagger) and can be accessed at:
`http://localhost:8080/swagger-ui.html`

## Stress Testing

A stress test can be performed using Docker Compose and Autocannon.

### Running the Stress Test
1. Use the following script to start the services for the stress test:
2. In project directory run
   ```base
   stress_test.bat
   ```

3. Wait for the services to start and then will return the result on bash
 
### Some Stress Test Results

#### With Redis Cache
- **19k requests** in **30.8s**, **1.37 GB** read.

#### Without Redis Cache
- **22k requests** in **31.36s**, **53.1 MB** read.
- **19k errors** (4k timeouts).