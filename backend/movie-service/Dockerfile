# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .

# Copy the entire source code
COPY src/ ./src/

# Build the application
RUN ./gradlew build

# Make port 3005 available to the world outside this container
EXPOSE 3005

# Run the application when the container starts
CMD ["java", "-jar", "build/libs/reservation-service.jar"]