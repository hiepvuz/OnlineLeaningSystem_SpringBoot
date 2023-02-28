FROM openjdk:8-jre

ADD ./target/*.jar app.jar
CMD ["java", "-Xmx200m", "-jar", "app.jar"]

EXPOSE 8888