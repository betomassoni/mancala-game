# Mancala API

## Overview
This is a sample project that demonstrates the integration of an Angular frontend and a Spring Boot API, which saves game data to an in-memory Redis database. It enables users to play the Mancala/Kalah game. The features available in the application include:

* Create new game
* Sow seeds


  ![game-screen.png](docs%2Fgame-screen.png)


This project consists of two main components:

1. **Angular Frontend:** A user interface developed in Angular that allows users to interact with the Mancala game, view game data, and make game moves.

2. **Spring Boot API:** A RESTful API developed in Java using the Spring Boot framework. This API manages the game logic, including business logic, data persistence in the in-memory Redis database, and communication with the frontend.

  ![main-techs.png](docs%2Fmain-techs.png)

## Architectural choices
Hexagonal architecture was used in this project. Also known as Ports and Adapters architecture, is an architectural pattern that aims to separate the core business logic of an application from external concerns, such as user interfaces, databases, and external services. It organizes the code into layers, with the application core at the center, surrounded by external layers that communicate through ports (interfaces) and adapters.

#### Architecture diagram
![app-architecture.drawio.png](docs%2Fapp-architecture.drawio.png)

## Tests
In this project unit tests were used with **Junit**, **Mockito** and **AssertJ** and for the end-2-end tests **Cucumber**. The test scenarios generated more than **90%** code coverage according to reports extracted with JaCoCo.
#### Test coverage using JaCoCo
![test-coverage.png](docs%2Ftest-coverage.png)


## How to run the API
Clone this repository and on the terminal, go to the **mancala_api** directory and run the following commands:
````
mvn clean package
docker compose up
````

## How to the frontend
Go to the **mancala_web** directory and run the following commands:
````
npm install
npm start
````

Then the application will run on the default port 4200: http://localhost:4200/

## How to run end-2-end tests with Cucumber
With the application running, execute the command below:
````
mvn clean test -f mancala-e2e/pom.xml -Dtest=JUnitRunner
````

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
- RestAssured
- Angular

## Next steps
- Implement a WebSocket system to allow two players to play the game in different browsers in real-time