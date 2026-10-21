# first stage: Build the application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
COPY pom.xml .
RUN ./mvnw package -DskipTests=true

# second stage: Run the application using a smaller JRE image
FROM eclipse-temurin:21-jre

WORKDIR /runningapp

COPY --from=builder /app/target/d13revision-0.0.1-SNAPSHOT.jar .

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

CMD ["java", "-jar", "d13revision-0.0.1-SNAPSHOT.jar"]
