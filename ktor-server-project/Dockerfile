# Build stage
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle buildFatJar --no-daemon

# Package stage
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar /app/ktor-server-project.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/ktor-server-project.jar"]
