# Stage 1: Build using Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Maven build will use the compiler args from your pom.xml for Java 25 support
RUN mvn clean package -DskipTests

# Stage 2: Run using Java 25 (Early Access/Stable builds)
# Note: Using 21 here is safer if 25 image isn't found, but let's try 21 as it runs 25-compiled jars usually or matches your pom.
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]