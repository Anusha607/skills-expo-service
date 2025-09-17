# Use OpenJDK as the base image
FROM eclipse-temurin:17-jdk AS build

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a new image for running the app
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
