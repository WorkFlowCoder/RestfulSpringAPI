# Product API

This project is a RESTful API for managing products. It allows users to create, update, retrieve, and delete products in a system. The API is built using **Spring Boot**, with **Jakarta Bean Validation** for validating requests, and uses **JUnit** for testing.

## Features

- **Create a product**: Add a new product to the system.
- **Retrieve products**: Get a list of all products or a specific product by ID.
- **Update a product**: Modify details of an existing product.
- **Delete a product**: Remove a product from the system.
- **Validation**: Automatic validation of requests using `@Valid` annotations.
  
## Requirements

- **Java 17** or higher
- **Maven 3.6+**
- **Spring Boot 3.0+**
- **H2 Database** (in-memory for testing)
- **Jakarta Bean Validation**

## Getting Started

### Clone the repository

```bash
git clone https://github.com/WorkFlowCoder/RestFulSpringAPI.git
cd RestFulSpringAPI
```
## Install dependencies

Make sure you have Maven installed, then run:

```bash
mvn clean install
```

## Run the application

To start the application:

```bash
mvn spring-boot:run
```

The API will be available at http://localhost:8080.

# API Endpoints

## Create a Product

- **URL**: `/products`
- **Method**: `POST`
- **Request Body**:

```json
{
  "name": "Smartphone X",
  "description": "Latest smartphone model",
  "image": "smartphone_x.png",
  "category": "Electronics",
  "price": 799.99,
  "quantity": 50,
  "internalReference": "PRODUCT_1",
  "shellId": 1,
  "inventoryStatus": "INSTOCK",
  "rating": 4
}
```

- **Success Response**: `201 Created`

## Retrieve All Products

- **URL**: `/products`
- **Method**: `GET`
- **Success Response**: `200 OK`
- **Response Example**:

```json
[
  {
    "id": 1,
    "name": "Smartphone X",
    "category": "Electronics",
    "price": 799.99,
    ...
  }
]
```

## Retrieve a Single Product

- **URL**: `/products/{id}`
- **Method**: `GET`
- **Success Response**: `200 OK`

## Update a Product

- **URL**: `/products/{id}`
- **Method**: `PUT`
- **Request Body**:

```json
{
  "name": "Smartphone Z",
  "description": "Updated smartphone model",
  "image": "smartphone_z.png",
  "category": "Electronics",
  "price": 899.99,
  "quantity": 30
}
```

- **Success Response**: `200 OK`

## Delete a Product

- **URL**: `/products/{id}`
- **Method**: `DELETE`
- **Success Response**: `204 No Content`

# Validation Errors

The API uses Jakarta Bean Validation for request validation. If a request fails validation, the response will include a 400 Bad Request status and a detailed message of what went wrong.

**Example of an invalid product creation request**:

```json
{
  "price": 899.99,
  "quantity": 30
}
```

**Response**:

```json
{
  "status": 400,
  "message": "Name is required"
}
```

# Running Tests

This project includes unit tests for the controller and service layers. To run the tests, use:

```bash
mvn test
```

**Tests include**:

- **Creation of valid and invalid products**
- **Update of products**
- **Handling of invalid requests** (e.g., missing required fields)
- **Error scenarios** (e.g., product not found)

# License

This API is provided as a simple example of a product management system for demonstration and educational purposes. It is not intended for production use, and there is no guarantee of full functionality, performance, or security.