FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY target/user-service-0.1.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]