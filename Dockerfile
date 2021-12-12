FROM openjdk:11
MAINTAINER Eray Kaya
ADD target/ReadingIsGood-0.0.1.jar ReadingIsGood-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ReadingIsGood-0.0.1.jar"]