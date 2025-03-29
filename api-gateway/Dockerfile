# Maven ile projenin derlenmesi için bir aşama
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Uygulama kaynak kodunu konteynerin içine kopyala
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Projeyi temizle ve derle
RUN mvn clean install -DskipTests

# OpenJDK'yi kullanan çalışma aşaması
FROM openjdk:21-jdk-slim

# Uygulama için çalışma dizini oluştur
WORKDIR /app

# Bir önceki aşamadan derlenmiş JAR dosyasını kopyala
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot'un default portu
EXPOSE 8085

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
