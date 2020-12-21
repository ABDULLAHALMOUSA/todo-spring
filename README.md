# Todo RESTful API
A simple todo app built with Spring Boot

### Prerequisites
- JDK 15
- Mysql 

You can run mysql in a docker image using:
`docker run --name todo-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=YOUR_PASSWORD -d mysql:latest`

### Steps to run
Start a mysql database named `todo_db` and set the following environment variables:
- DB_URL
- DB_USERNAME
- DB_PASSWORD

Run server in your IDE or with `./mvnw spring-boot:run`
