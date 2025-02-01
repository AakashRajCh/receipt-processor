# Receipt Processor

## Overview
The Receipt Processor is a Spring Boot application that processes receipts and calculates reward points based on various criteria.

## Features
- Process receipts and generate unique IDs.
- Calculate reward points for processed receipts.
- Handle duplicate receipts and invalid receipt IDs.

## Technologies Used
- Java
- Spring Boot
- Maven
- Docker
- Swagger

## Getting Started

### Prerequisites (only if not pulling Docker image)
- Java 11 or higher
- Maven 3.6.0 or higher
- Docker

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/AakashRajCh/receipt-processor.git
    cd receipt-processor
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Running with Docker
1. Pull the Docker image:
    ```sh
    docker pull aakashrajch/personalprojects:receiptprocessor.1
    ```

2. Run the Docker container:
    ```sh
    docker run -p 8080:8080 aakashrajch/personalprojects:receiptprocessor.1
    ```

## API Endpoints

### Process Receipt
- **URL:** `/receipts/process`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "retailer": "Target",
        "purchaseDate": "2023-03-10",
        "purchaseTime": "15:30",
        "items": [
            {
                "shortDescription": "Coke 1L",
                "price": "2.50"
            }
        ],
        "total": "2.50"
    }
    ```
- **Response:**
    ```json
    {
        "id": "unique-receipt-id"
    }
    ```

### Get Points
- **URL:** `/receipts/{id}/points`
- **Method:** `GET`
- **Response:**
    ```json
    {
        "points": 10
    }
    ```
## Viewing API Documentation
To view the API documentation using Swagger:
1. Ensure the application is running.
2. Open a web browser and navigate to: `http://localhost:8080/swagger-ui.html`

## Running Tests
To run unit and integration tests, use the following command:
```sh
mvn test