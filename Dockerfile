FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/Indexer-1.0.2.jar /app/target/Indexer-1.0.2.jar
EXPOSE 4567
CMD ["java", "-jar", "target/Indexer-1.0.2.jar"]