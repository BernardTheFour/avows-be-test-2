# Project

This project is for Avows Technical Test

## Application Features
 
- Kafka Microservice: simulate receiving data from another service by triggering API
- Liquibase DB Migration: robust database change management
- Swagger API Documentation: easy and comprehensive API documentation with nice UI
- Mockito: mock class and object to fully isolate the testing environment
- Docker Compose: easy to run and setup working environment for developer

## Prerequisites

- Windows 10 or later
- WSL 2 installed
- A WSL 2 Linux distribution installed (e.g., Ubuntu)
- Docker Engine installed and running on WSL

## Usage

### Running Docker Compose

1. Navigate to root project directory containing the `docker-compose.yml` file:

    ```bash
    cd /path/to/your/project
    ```

2. Start the services defined in your `docker-compose.yml` file:

    ```bash
    docker compose up
    ```

3. To run Docker Compose in detached mode (in the background):

    ```bash
    docker compose up -d
    ```

4. To stop the running services:

    ```bash
    docker compose down
    ```

### Running The Program

1. Navigate to root project directory containing the `docker-compose.yml` file:

    ```bash
    cd /path/to/your/project
    ```

2. Open pgAdmin UI and login using docker-compose pgAdmin4 creds email and password

3. Register new connections by using these information from docker-compose

    ```
    Connection hostname: db-postgres12
    port: 5432
    username: postgres
    password: postgres
    ```

4. Create new database with name 'demo' if database doesn't exist

5. Run bash script defined in the `run.sh` file:

    ```bash
    sh run.sh
    ```

### Swagger API Documentation

This application is running on port 8080.

To access the API documentation, hit http://localhost:8080/swagger-ui/index.html

### PostgreSQL pgAdmin UI

After running docker compose, pgAdmin UI could be accessed from browser.

To access the UI, hit http://localhost:1010/browser/

### Postman API Docs

Postman API documentation can be downloaded [here](/avows-betest-api.postman_collection.json)
