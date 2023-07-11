package pl.jojczykp.micrud.repositories;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

@MicronautTest
class CarsRepositoryTest {

    public static final Car CAR_1 = new Car("PQ12BBC", "Bugatti", Colour.YELLOW);
    public static final Car CAR_2 = new Car("ST12UWZ", "Mercedes", Colour.BLUE);

    @Inject
    CarsRepository carsRepository;

    @Test
    void shouldSaveSingleCar() {
        carsRepository.save(CAR_1).block();

        Flux<Car> saved = carsRepository.findAll();

        StepVerifier.create(saved)
                .expectNextMatches(car ->
                        Objects.nonNull(car.id()) &&
                        Objects.equals(car.regNumber(), CAR_1.regNumber()) &&
                        Objects.equals(car.make(), CAR_1.make()) &&
                        Objects.equals(car.colour(), CAR_1.colour()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldSaveMultipleCars() {
        carsRepository.saveAll(List.of(CAR_1, CAR_2)).blockLast();

        Flux<Car> saved = (carsRepository.findAll());

        StepVerifier.create(saved)
                .expectNextMatches(car ->
                        Objects.nonNull(car.id()) &&
                        Objects.equals(car.regNumber(), CAR_1.regNumber()) &&
                        Objects.equals(car.make(), CAR_1.make()) &&
                        Objects.equals(car.colour(), CAR_1.colour()))
                .expectNextMatches(car ->
                        Objects.nonNull(car.id()) &&
                        Objects.equals(car.regNumber(), CAR_2.regNumber()) &&
                        Objects.equals(car.make(), CAR_2.make()) &&
                        Objects.equals(car.colour(), CAR_2.colour()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldFindByRegNumber() {
        String regNumber = "AB01CDE";
        Car saved = new Car(regNumber, "Honda", Colour.PINK);
        carsRepository.save(saved).block();

        Mono<Car> maybeFound = carsRepository.findByRegNumber(regNumber);

        StepVerifier.create(maybeFound)
                .expectNextMatches(car ->
                        Objects.nonNull(car.id()) &&
                        Objects.equals(car.regNumber(), saved.regNumber()) &&
                        Objects.equals(car.make(), saved.make()) &&
                        Objects.equals(car.colour(), saved.colour()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldNotFindByRegNumber() {
        Car saved = new Car("UW67QWE", "Mazda", Colour.BROWN);
        carsRepository.save(saved).block();

        Mono<Car> maybeFound = carsRepository.findByRegNumber("ANOTHER");

        StepVerifier.create(maybeFound)
                .expectComplete()
                .verify();
    }
}
