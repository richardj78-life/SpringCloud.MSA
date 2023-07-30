FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY target/order-service-0.1.jar OrderService.jar
ENTRYPOINT ["java", "-jar", "OrderService.jar"]