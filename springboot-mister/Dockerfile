# Build usando openjdk 21
FROM openjdk:21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Ejecutar con openjdk 21 jre slim
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
