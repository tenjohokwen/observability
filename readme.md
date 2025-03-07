# Context
* This project is a demonstration of spring boot observability using the grafana stack.
* The code was written to go along with [this article](https://codemajor.de/2025/03/07/spring-boot-observability/)

## Steps to run and observe
1. docker compose up (run this from the terminal in the root folder of this project)
2. mvn spring-boot:run (run this from the terminal in the root folder of this project)
3. invoke the bookResource endpoints using the following base url: http://localhost:8080/v1/book/(endpoint)
4. Open grafana on port 3000 (http://localhost:3000) and observe