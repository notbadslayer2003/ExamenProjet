FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

EXPOSE 8081

CMD ["java", "-jar", "build/libs/StuttgartSpeed-BE-0.0.1-SNAPSHOT.jar"]