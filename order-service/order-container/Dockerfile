FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/order-container-0.0.1.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]