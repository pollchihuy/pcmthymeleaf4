#FROM openjdk:21-oraclelinux8
#FROM khipu/openjdk21-alpine
FROM alpine/java:21-jre

COPY target/paul-fe.jar /apapun/paul-fe.jar
CMD ["java","-jar","/apapun/paul-fe.jar"]