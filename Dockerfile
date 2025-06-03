FROM eclipse-temurin:17-jdk as builder

WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY --from=builder /app/build/libs/Mova-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar /app.jar"]