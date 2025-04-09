#Use Maven for build
FROM maven:3.9.8-amazoncorretto-21 as build
LABEL authors="HoangSinh"
WORKDIR /app
COPY ./Cardealer ./Cardealer
WORKDIR /app/Cardealer
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-jdk-slim
WORKDIR /app
COPY --from=build /app/Cardealer/target/*.jar cardealer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cardealer.jar"]