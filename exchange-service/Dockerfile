FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app/exchange-service

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app/exchange-service

COPY --from=build /app/exchange-service/build/libs/*.jar exchange-service.jar

EXPOSE $EXCHANGE_SERVER_PORT

ENTRYPOINT ["java", "-jar", "exchange-service.jar"]