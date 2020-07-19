# JAX-RS REST API - SOA Assignment 1 Summer Term 2020

Implement a beverage store as specified in the assignment description.
Your project must be buildable with gradle (the easiest way to achieve this is using this template).
If your project can't be run with `gradlew run` to start the JAX-RS server, your submission will be marked with 0 points.
The server is available at `localhost:9999/v1` per default. 
This can be configured via `src/main/resources/config.properties`.
 
To discover the different resources, methods and data schemas use the [Swagger Editor](https://editor.swagger.io/#) and the `openapi.yaml` file.
Also include a swagger UI resource to enable displaying swagger UI as in our demo project.

## Artefacts, you have to submit
- Source Code
- openapi.yaml
- insomnia.json

## Completed Tasks

# Use Cases
User: View Beverages? (GET)
User: Filtering MIN, MAX?
User: View Order via Order ID? (GET)
Employee: Create beverages
Employee: Update beverages
Employee: Delete beverages

# REST API

Correct HTTP verbs
Correct status codes
Beverage DTO
Pagination
Appropriate API path desig
Parameter Validation
Error Message for beverages


## Inprogress Tasks
User: Place Orders? (POST) (0/2)
Open API Specification implementation
Order DTO implementation
User: Change Order as long as it is not PROCESSED? (PUT)
Employee: Commits order and changes state to PROCESSED? (PUT) (0/1.5)
Concurrency Handling
Polymorphism
Error Message for orders
Idempotency for POST










