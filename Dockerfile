# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-oracle
EXPOSE 8181

# Copy the jar file to the container
COPY target/CustomerManagementService.jar CustomerManagementService.jar

# Run the jar file
<<<<<<< Updated upstream:Dockerfile
ENTRYPOINT ["java", "-jar", "app.jar"]
=======
ENTRYPOINT ["java", "-jar", "CustomerManagementService.jar"]
>>>>>>> Stashed changes:dockerFile
