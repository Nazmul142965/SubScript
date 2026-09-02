# Stage 1: Build
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# এখানে আমরা নির্দিষ্ট নাম না দিয়ে জাস্ট target ফোল্ডারের জার ফাইলটি কপি করছি
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]