FROM openjdk:11-jre-slim

LABEL maintainer="noisonnoiton"

ADD target/awesome-account-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]