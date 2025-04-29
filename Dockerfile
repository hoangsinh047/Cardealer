# -------- STAGE 1: Build app with Maven ----------
FROM maven:3.9.8-amazoncorretto-21 as build
LABEL authors="HoangSinh"
WORKDIR /app
COPY . ./Cardealer
WORKDIR /app/Cardealer
RUN mvn clean package -DskipTests
# -------- STAGE 2: Run with JDK only ----------
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/Cardealer/target/*.jar cardealer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cardealer.jar"]