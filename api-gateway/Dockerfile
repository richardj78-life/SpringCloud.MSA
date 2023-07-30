FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY target/api-gateway-0.1.jar ApiGateway.jar
ENTRYPOINT ["java", "-jar", "ApiGateway.jar"]