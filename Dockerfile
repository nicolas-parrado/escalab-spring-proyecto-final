FROM openjdk:8-jdk-alpine as builder

WORKDIR /build

COPY ./backend-spring/mvnw .
COPY ./backend-spring/.mvn ./.mvn
COPY ./backend-spring/pom.xml .
RUN ./mvnw dependency:go-offline

COPY ./backend-spring/src /build/src
RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=builder /build/target/backend-spring-1.0.0.jar /app/my-app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "/app/my-app.jar"]