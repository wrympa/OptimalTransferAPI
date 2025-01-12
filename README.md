# OptimalTransferAPI

**OptimalTransferAPI** is a Spring Boot-based RESTful API that helps calculate the optimal transfer of goods based on weight and cost constraints. This API is designed to handle requests with different transfer options and calculate the best solution that respects a maximum weight constraint.

## Features

- **Find Optimal Transfers**: Given a list of available transfers (with weight and cost), it calculates the most profitable set of transfers that stays within the maximum weight.
- **RESTful API**: The API provides a simple interface for requesting optimal transfer solutions via HTTP POST.
- **Error Handling**: Returns appropriate HTTP error codes for failed operations (e.g., internal server errors).
- **Spring Boot**: The application is built using Spring Boot, which makes it easy to run as a standalone application or deploy it in a server environment.

### Prerequisites:
- Java 17+: The project is built using Java 17.
- Maven: Used to build the project.
- IDE: IntelliJ IDEA, Eclipse, or any IDE with Java support.

## API Endpoints

### `POST /api/transfers/findOptimal`

This endpoint calculates the optimal transfers for the given request.

#### Request Body Example:
```json
{
  "maxWeight": 10,
  "availableTransfers": [
    {
      "weight": 2,
      "cost": 3
    },
    {
      "weight": 3,
      "cost": 4
    },
    {
      "weight": 4,
      "cost": 5
    }
  ]
}
```
maxWeight and availableTransfers are required fields.


#### Reply Body Example:
```json
{
  "selectedTransfers": [
    {
      "weight": 5,
      "cost": 10
    },
    {
      "weight": 10,
      "cost": 20
    }
  ],
  "totalCost": 30,
  "totalWeight": 15
}
```

There are provided test cases for the API.

to run API locally, you can run the following commands:
```shell
mvn clean package
```
this will create a .jar file in the target folder, then you can run the jar file using the following command:
```shell 
java -jar target/your-project-name-1.0-SNAPSHOT.jar
```
then open another terminal, and you can run the following command to test the API:
```shell
curl -X POST http://localhost:8080/api/transfers/findOptimal \
     -H "Content-Type: application/json" \
     -d '{"maxWeight": 10, "availableTransfers":[{"weight": 2, "cost": 3}, {"weight": 3, "cost": 4}, {"weight": 4, "cost": 5}]}'
```
