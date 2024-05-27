# Use a base image with OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/ejercicio-0.0.1-SNAPSHOT.jar app.jar

#EXPOSE 8080

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]

CMD [ "-?" ]

