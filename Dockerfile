FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Young.K"

WORKDIR /usr/src/app

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV USE_PROFILE prod

ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "./app.jar"]