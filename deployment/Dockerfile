FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY applications/app-service/build/libs/*.jar ms-authentication.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar ms-authentication.jar" ]
