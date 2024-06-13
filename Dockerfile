# Use JDK 17 as the base image
FROM gradle:8.6.0-jdk17


WORKDIR /app


COPY build/libs/*.jar demo_backend-0.0.1-SNAPSHOT.jar

#demo_backend-0.0.1-SNAPSHOT-plain.jar


# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","demo_backend-0.0.1-SNAPSHOT.jar"]