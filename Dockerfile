## Image for build
FROM gradle:6.7.1-jdk11 as builder
ARG GRADLE_OPTS="-Dorg.gradle.daemon=false ${GRADLE_OPTS}"
WORKDIR /app
COPY . .
RUN gradle stage

## Image for deploy
#FROM gcr.io/distroless/java:debug
FROM openjdk:11-slim
WORKDIR /app
COPY --from=builder /app/server/build/install/server-shadow/lib/*.jar .
#CMD ["server-1.1-SNAPSHOT-all.jar"]
CMD ["/bin/sh", "-c", "java -jar *.?ar"]
