FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY target/item-service-0.1.jar ItemService.jar
ENTRYPOINT ["java", "-jar", "ItemService.jar"]