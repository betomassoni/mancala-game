FROM openjdk:17
MAINTAINER Roberto Massoni
ARG JAR_FILE=mancala-application/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]