# Use an official OpenJDK runtime as a parent image
FROM openjdk:17.0.1-jdk-slim-buster
# Set the working directory in the container
WORKDIR /auth-service

# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradlew.bat .

#Copy the required gradle files
COPY gradle/ ./gradle/

# Copy the entire source code
COPY src/ ./src/

# Build the application
RUN ./gradlew build -x test

# Make port 3202 available to the world outside this container
EXPOSE 3202

# Run the application when the container starts
CMD ["java", "-jar", "build/libs/auth-service-0.0.1-SNAPSHOT.jar"]