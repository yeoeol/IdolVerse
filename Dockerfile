FROM azul/zulu-openjdk:17-latest
ARG JAR_FILE=build/libs/*.jar
ARG PROFILES=dev
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-jar", "app.jar"]
