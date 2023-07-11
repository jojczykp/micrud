package pl.jojczykp.micrud.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.repositories.CarsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/cars")
public class CarsController {

    private final CarsRepository carsRepository;

    public CarsController(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @Get
    public Flux<Car> getAll() {
        return carsRepository.findAll();
    }

    @Get("/{regNumber}")
    public Mono<Car> getByRegNumber(String regNumber) {
        return carsRepository.findByRegNumber(regNumber);
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<Car>> create(@Body Car car) {
        Mono<Car> saved = carsRepository.save(car);
        return saved.map(HttpResponse::created);
    }
}
