FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/scala-3.*/api_3-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]