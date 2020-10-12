# syntax=docker/dockerfile:experimental
FROM maven:3-jdk-11 AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-alpine
LABEL "Author=Tatu Soininen (admin@viest.in)"
LABEL "in.viest.scrum-server.description=Scrum Poker backend"

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

RUN addgroup -S demo && adduser -S demo -G demo
USER demo

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]

COPY . /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)