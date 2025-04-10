FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app/gateway-server

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app/gateway-server

COPY --from=build /app/gateway-server/build/libs/*.jar gateway-server.jar

EXPOSE $GATEWAY_SERVER_PORT

ENTRYPOINT ["java", "-jar", "gateway-server.jar"]