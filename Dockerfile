FROM 170247361816.dkr.ecr.ap-northeast-2.amazonaws.com/openjdk:11-jre-slim
# FROM openjdk:11-jre-slim-buster

LABEL maintainer="noisonnoiton"

COPY target/awesome-account-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]