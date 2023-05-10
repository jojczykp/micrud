FROM ghcr.io/graalvm/native-image:ol9-java17-22.3.2 AS builder

RUN microdnf -y install findutils

WORKDIR /app

COPY ./gradlew .
COPY ./gradle ./gradle
COPY ./build.gradle .
COPY ./settings.gradle .
COPY ./gradle.properties .
COPY ./src ./src

RUN ./gradlew nativeCompile --no-daemon


FROM ghcr.io/graalvm/native-image:ol9-java17-22.3.2
EXPOSE 8080
WORKDIR /app
COPY --from=builder /app/build/native/nativeCompile/micrud /app
ENTRYPOINT ["/app/micrud"]
