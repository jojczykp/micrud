package pl.jojczykp.micrud.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.repositories.CarsRepository;

import java.util.Optional;

@Controller("/cars")
@ExecuteOn(TaskExecutors.IO)
public class CarsController {

    private final CarsRepository carsRepository;

    public CarsController(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @Get
    Iterable<Car> getAll() {
        return carsRepository.findAll();
    }

    @Get("/{regNumber}")
    Optional<Car> getByRegNumber(String regNumber) {
        return carsRepository.findByRegNumber(regNumber);
    }

}