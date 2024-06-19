# Use JDK 17 as the base image
FROM gradle:8.6.0-jdk17 as builder

WORKDIR /app

# Copy your source code and wait-for-it.sh into the container
COPY . .
COPY wait-for-it.sh wait-for-it.sh

# Make wait-for-it.sh executable
RUN chmod +x wait-for-it.sh

# Build the project inside the Docker container
RUN gradle clean build -x test

# Start a new, final image to reduce the image size
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the built jar file from the "builder" image into this new image
COPY --from=builder /app/build/libs/*.jar demo_backend-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","demo_backend-0.0.1-SNAPSHOT.jar"]
