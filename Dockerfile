FROM adoptopenjdk:11-jdk-hotspot

ADD target/app.jar /deployments/app.jar
