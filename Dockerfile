# Builder
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
COPY bikes-api-rest/pom.xml bikes-api-rest/
COPY bikes-application/pom.xml bikes-application/
COPY bikes-domain/pom.xml bikes-domain/
COPY bikes-infrastructure/pom.xml bikes-infrastructure/
COPY bikes-migration/pom.xml bikes-migration/

RUN mvn dependency:go-offline

COPY . .

RUN mvn package

# Runner
FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/bikes-api-rest/target/bikes-api-rest-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "bikes-api-rest-0.0.1-SNAPSHOT.jar"]