FROM adoptopenjdk/openjdk11:ubi
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]