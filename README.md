# Mancala API
This Java application enables users to play Mancala/Kalah game.
The features available in the application are:
* Create new game
* Sow seeds

## Architectural choices
Hexagonal architecture was used in this project. Also known as Ports and Adapters architecture, is an architectural pattern that aims to separate the core business logic of an application from external concerns, such as user interfaces, databases, and external services. It organizes the code into layers, with the application core at the center, surrounded by external layers that communicate through ports (interfaces) and adapters.

#### Architecture diagram
![app-architecture.drawio.png](docs%2Fapp-architecture.drawio.png)

## Tests
In this project unit tests were used with **Junit**, **Mockito** and **AssertJ** and for the end-2-end tests **Cucumber**. The test scenarios generated more than **90%** code coverage according to reports extracted with JaCoCo.
#### Test coverage using JaCoCo
![test-coverage.png](docs%2Ftest-coverage.png)


## Documentation
The OpenAPI specification is used to define and document the REST API of the application.
Both documentations can be viewed by accessing the URL below after running the project.
#### Swagger
````
http://localhost:8080/swagger-ui/index.html
````
#### OpenAPI
````
http://localhost:8080/v3/api-docs
````

## How to run the application
Clone this repository and on the terminal, go to the root directory and run the following commands:
````
mvn clean package
docker compose up
````

## How to run end-2-end tests with Cucumber
With the application running, execute the command below:
````
mvn clean test -f mancala-e2e/pom.xml -Dtest=JUnitRunner
````

## Used frameworks and tools
- Spring Boot
- Lombok
- AssertJ
- Mockito
- Junit 5
- Cucumber
- Mapstruct
- JaCoCo
- CheckStyle
- Maven
- Redis
- Swagger2
- Docker