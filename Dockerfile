FROM openjdk:17
ARG jarFile=target/*.jar
WORKDIR /opt/app
COPY ${jarFile} library.jar
EXPOSE 10906
ENTRYPOINT ["java", "-jar", "library.jar"]
