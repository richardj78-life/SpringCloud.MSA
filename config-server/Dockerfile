FROM azul/zulu-openjdk-alpine:11-latest
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY target/config-server-0.1.jar ConfigServer.jar
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]