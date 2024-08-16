FROM openjdk:17-oracle
COPY build/libs/*.jar magic.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/magic.jar"]