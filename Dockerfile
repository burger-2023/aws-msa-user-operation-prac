FROM amazoncorretto:17.0.7-al2023-headless

VOLUME /tmp

COPY build/libs/aws-msa-user-service-1.0.jar user-service.jar

ENTRYPOINT ["java","-jar","user-service.jar"]