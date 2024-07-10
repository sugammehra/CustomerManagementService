# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-oracle
EXPOSE 8181

# Copy the jar file to the container
COPY target/CustomerManagementService.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]