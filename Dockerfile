FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN mvn clean package

EXPOSE 8080
EXPOSE 5701
EXPOSE 4567

CMD ["java", "-jar", "target/Indexer-1.0-SNAPSHOT.jar"]

