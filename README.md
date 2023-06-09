# MiCRUD

Pet project to get hands on basics of Micronaut, GraalVM and Reactor.

## Run

### From Gradle

```shell
./gradlew run
```

### From Native Image

```shell
./gradlew nativeCompile
```

```shell
build/native/nativeCompile/micrud
```

### From Docker

```shell
docker build -t micrud .
```

```shell
docker run -it --rm -p 8080:8080 micrud
```

## Test

```shell
curl http://localhost:8080/cars -H 'Content-Type: application/json' -d '{"regNumber":"XX99ZZZ","make":"Citroën","colour":"BROWN"}' | jq .
```

```shell
curl http://localhost:8080/cars | jq .
```
