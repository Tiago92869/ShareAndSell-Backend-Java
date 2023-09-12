# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring JAR file into the container at the working directory
COPY target/shop.service.jar /app/shop-service.jar

# Expose the port your Spring application will run on
EXPOSE 3030

# Specify the command to run your Spring application
CMD ["java", "-jar", "shop-service.jar"]