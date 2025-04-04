FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Add a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy JAR file
COPY --chown=spring:spring target/greenbook-0.0.1-SNAPSHOT.jar /app/app.jar

# Create directory for file uploads
RUN mkdir -p /app/uploads
RUN chmod 755 /app/uploads

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]