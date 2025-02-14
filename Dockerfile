FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/BD_LOJA-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

CMD ["java", "-jar", "app.jar"]