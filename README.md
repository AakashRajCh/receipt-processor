
# Receipt Processor

## Overview

The Receipt Processor is a Spring Boot application that processes receipts and calculates reward points based on various criteria. It also includes a React frontend for interacting with the APIs. The entire application is deployed in a cloud environment using Heroku, making it easily accessible and scalable.

## Features

-   Process receipts and generate unique IDs.
-   Calculate reward points for processed receipts.
-   User-friendly frontend built with React.

## Technologies Used

-   Java
-   Spring Boot
-   Maven
-   Docker
-   Swagger
-   React
-   Heroku

## Getting Started

### Prerequisites (only if not pulling Docker image)

-   Java 11 or higher
-   Maven 3.6.0 or higher
-   Docker

### Installation

1.  Clone the repository:

    ```sh
    git clone https://github.com/AakashRajCh/receipt-processor.git
    cd receipt-processor
    
    ```

2.  Build the project:

    ```sh
    mvn clean install
    
    ```

3.  Run the application:

    ```sh
    mvn spring-boot:run
    
    ```
### Access with URL (Hosted on Cloud (Heroku))

The entire application, including the React frontend and Spring Boot backend, is hosted on Heroku. You can access it by navigating to the following link:

[https://receipt-processor-d7bc841c60a4.herokuapp.com/](https://receipt-processor-d7bc841c60a4.herokuapp.com/)


### Running with Docker

1. Pull the Docker image:

    ```sh
    docker pull aakashrajch/fetch-rewards-receipt-processor-challenge
    ```

2. Run the Docker container:

    ```sh
    docker run -p 8080:8080 aakashrajch/fetch-rewards-receipt-processor-challenge
    ```

### Testing the API

Use Postman or a similar tool to test the API endpoints:

#### Submit a Receipt (POST Request)

- **Endpoint:** `http://localhost:8080/receipts/process`
- **Description:** 
  - If a valid receipt is submitted, it returns an ID.
  - If not, it returns "The receipt is invalid".

#### Get Points for a Receipt (GET Request)

- **Endpoint:** `http://localhost:8080/receipts/{id}/points`
- **Description:**
  - If the receipt exists, it returns the points for that receipt.
  - If the receipt does not exist, it returns "No receipt found for that ID".

## API Endpoints

### Process Receipt

-   **URL:** `/receipts/process`
-   **Method:** `POST`
-   **Request Body:**

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

-   **Response:**

    ```json
    {
        "id": "unique-receipt-id"
    }
    
    ```


### Get Points

-   **URL:** `/receipts/{id}/points`
-   **Method:** `GET`
-   **Response:**

    ```json
    {
        "points": 10
    }
    
    ```


## Viewing API Documentation

To view the API documentation using Swagger:

1.  Ensure the application is running.
2.  Open a web browser and navigate to: `http://localhost:8080/swagger-ui.html`

## Running Tests

To run unit and integration tests, use the following command:

```sh
mvn test

```
## Application Hosting & Deployement

The entire application, including the React frontend and Spring Boot backend, is hosted on Heroku. You can access it at: [https://receipt-processor-d7bc841c60a4.herokuapp.com/](https://receipt-processor-d7bc841c60a4.herokuapp.com/)