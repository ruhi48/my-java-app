# Dockerfile
FROM openjdk:11-jre-slim

WORKDIR /app
COPY target/my-java-app.jar /app/my-java-app.jar

CMD ["java", "-jar", "/app/my-java-app.jar"]
