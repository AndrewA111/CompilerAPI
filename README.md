## CompileAPI - Compiler Web-service for MSc Project

API for compiling and testing code sent from MSc Project Web-App

## Setup

### Building the Docker Image

Code is executed in containers using Docker. Please insuure Docker is installed. 

Before running the application the Docker image must be built.Navigate to `/MountDockerfile` and execute the following code:

> `docker build -t mount_compiler .` 

A Docker image titled `mount_compiler` should be build, you can check by entering:

>`docker image ls`

### Building the Application

Maven is used as the build tool. In the root directory enter the following:

>`mvn clean package`

### Running the Application

In the root directory, enter the following:

>`java -jar target/CompileAPI-1.0-SNAPSHOT.jar server`

The application should then begin running on 0.0.0.0:8000.
