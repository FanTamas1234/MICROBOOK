FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app/book-service

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app/book-service

COPY --from=build /app/book-service/build/libs/*.jar book-service.jar

EXPOSE $BOOK_SERVER__PORT

ENTRYPOINT ["java", "-jar", "book-service.jar"]