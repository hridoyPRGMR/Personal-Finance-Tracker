FROM openjdk:21

WORKDIR /app

# Copy the jar file to the container
COPY target/personal-finance-0.0.1-SNAPSHOT.jar app.jar

# Expose the port application runs on
EXPOSE 9090

# run the application
ENTRYPOINT ["java","-jar","app.jar"]