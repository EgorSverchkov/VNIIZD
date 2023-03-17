FROM openjdk:18
MAINTAINER Sverchkov Egor
COPY vniizd-0.0.1-SNAPSHOT.jar vniizd.jar
ENTRYPOINT ["java","-jar","/vniizd.jar"]
