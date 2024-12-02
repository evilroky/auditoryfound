FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=qwerty
ENV MYSQL_DATABASE=found
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=qwerty

COPY found.sql /docker-entrypoint-initdb.d/

FROM gradle:8.10.2-jdk23 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

FROM openjdk:23-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]