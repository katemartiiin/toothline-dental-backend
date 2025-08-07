# Use a full JDK image to build the app
FROM eclipse-temurin:21 as builder

WORKDIR /app

# Copy the source code
COPY . .

# Grant permission to Gradle wrapper if needed
RUN chmod +x ./gradlew

# Build the fat jar
RUN ./gradlew bootJar

# -----------------------

# Use a smaller image just to run the jar
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]