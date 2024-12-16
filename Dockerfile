FROM maven:3.9.9-eclipse-temurin-17 as build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/*.jar spring.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring.jar"]