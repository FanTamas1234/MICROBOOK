FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app/config-server

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app/config-server

COPY --from=build /app/config-server/build/libs/*.jar config-server.jar

EXPOSE $CONFIG_SERVER_PORT

ENTRYPOINT ["java", "-jar", "config-server.jar"]