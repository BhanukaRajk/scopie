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

# Make port 3202 available to the world outside this container
EXPOSE 3202

# Run the application when the container starts
CMD ["java", "-jar", "build/libs/auth-service-0.0.1-SNAPSHOT.jar"]