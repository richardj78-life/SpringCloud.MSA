FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY target/discovery-server-0.1.jar DiscoveryServer.jar
ENTRYPOINT ["java", "-jar", "DiscoveryServer.jar"]