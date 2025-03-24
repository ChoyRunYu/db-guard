FROM openjdk:17
WORKDIR /opt/db-guard
ADD ./target/db-guard-1.0.0.jar db-guard.jar
EXPOSE 8083
CMD ["java", "-jar", "db-guard.jar"]