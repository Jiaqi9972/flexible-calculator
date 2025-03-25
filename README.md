# Flexible Calculator

A Spring Boot calculator service that implements basic operations (ADD, SUBTRACT, MULTIPLY, DIVIDE) with a focus on extensibility and support for chained calculations.

## Features

- Basic calculation operations
- Chained calculation operations
- Extensible design with Strategy pattern
- Consistent error handling
- Comprehensive test coverage
- API documentation with Swagger
- Application monitoring with Spring Actuator

## Getting Started

### Installation

Clone the repository:

```bash
git clone https://github.com/Jiaqi9972/flexible-calculator.git
cd flexible-calculator
```

Build the application:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start on port 8090.

## API Usage

The calculator service exposes two main endpoints:

### 1. Basic Calculation

**Endpoint:** `POST /api/v1/calculator/calculate`

Performs a single calculation operation between two numbers.

**Request Body:**

```json
{
  "operation": "ADD",
  "num1": 10,
  "num2": 5
}
```

**Example Operations:**

- `ADD` - Addition
- `SUBTRACT` - Subtraction
- `MULTIPLY` - Multiplication
- `DIVIDE` - Division

**Example Response:**

```json
{
  "code": "200",
  "message": "Success",
  "data": 15
}
```

### 2. Chain Calculation

**Endpoint:** `POST /api/v1/calculator/chain`

Performs multiple operations in sequence starting with an initial value.

**Request Body:**

```json
{
  "initialValue": 10,
  "operations": [
    {
      "operation": "ADD",
      "value": 5
    },
    {
      "operation": "MULTIPLY",
      "value": 2
    },
    {
      "operation": "DIVIDE",
      "value": 5
    }
  ]
}
```

**Example Response:**

```json
{
  "code": "200",
  "message": "Success",
  "data": 6
}
```

## API Documentation

Swagger UI is available at: http://localhost:8090/swagger-ui.html

You can use the Swagger UI to:

- Explore the available API endpoints
- Test API endpoints directly
- View request/response models
- View possible error responses

## Application Monitoring

Spring Actuator endpoints are available to monitor the application:

- Health check: http://localhost:8090/actuator/health
- Info: http://localhost:8090/actuator/info
- Metrics: http://localhost:8090/actuator/metrics

## Running Tests

The application includes comprehensive unit and integration tests.

Run all tests:

```bash
mvn test
```

Run only unit tests:

```bash
mvn test -Dtest="org/ebay/flexiblecalculator/unit/**/*Test.java"
```

Run only integration tests:

```bash
mvn test -Dtest="org/ebay/flexiblecalculator/integration/**/*Test.java"
```

## Design

The application follows these design principles:

1. **Open-Closed Principle**: The calculator is designed to be extended with new operations without modifying existing code.

2. **Strategy Pattern**: Different operations are implemented as strategies.

3. **Chain of Responsibility**: The chained calculator allows operations to be applied sequentially.

4. **IoC Compatible**: Designed for dependency injection to enable easy testing.

## Error Handling

The application provides consistent error handling with appropriate HTTP status codes and user-friendly error messages:

- 400 Bad Request - For invalid operations, parameters, or calculation errors
- 500 Internal Server Error - For unexpected system errors