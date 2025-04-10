FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app/user-service

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app/user-service

COPY --from=build /app/user-service/build/libs/*.jar user-service.jar

EXPOSE $USER_SERVER_PORT

ENTRYPOINT ["java", "-jar", "user-service.jar"]