## CompileAPI - Compiler Web-service for MSc Project

API for compiling and testing code sent from MSc Project Web-App

## Setup

### Building the Docker Image

Code is executed in containers using Docker. Before running the application the Docker image must be build.

Navigate to '/MountDockerfile' and execute the following code

How to start the CompileAPI application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/CompileAPI-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
