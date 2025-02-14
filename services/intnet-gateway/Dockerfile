FROM openjdk:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y dnsutils iputils-ping

COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]