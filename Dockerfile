FROM maven:3.9.11-eclipse-temurin-25-alpine AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM eclipse-temurin:25-jre-alpine
COPY --from=build /app/target/*.jar /application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]
