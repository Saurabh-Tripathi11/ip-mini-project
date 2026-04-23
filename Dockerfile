# ---- Stage 1: Build ----
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# ---- Stage 2: Run ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Groq API key can be passed at runtime:
#   docker run -e GROQ_API_KEY=your_key ...
ENV GROQ_API_KEY=""

ENTRYPOINT ["java", "-jar", "app.jar"]
