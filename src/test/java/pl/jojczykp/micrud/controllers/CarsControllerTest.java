package pl.jojczykp.micrud.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;
import pl.jojczykp.micrud.repositories.CarsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MicronautTest
class CarsControllerTest {

    @Inject
    CarsController carsController;

    @Inject
    CarsRepository carsRepository;

    @MockBean(CarsRepository.class)
    public CarsRepository carRepository() {
        return mock(CarsRepository.class);
    }

    @Test
    void shouldGetAll() {
        Car car1 = new Car(1L, "XY01ABC", "Hyundai", Colour.RED);
        Car car2 = new Car(2L, "AB02XYZ", "Peugeot", Colour.BLUE);
        given(carsRepository.findAll()).willReturn(Flux.just(car1, car2));

        Flux<Car> returned = carsController.getAll();

        StepVerifier.create(returned)
                .expectNext(car1)
                .expectNext(car2)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldGetByValidRegNumber() {
        String regNumber = "FP77RFV";
        Car car = new Car(1L, regNumber, "Škoda", Colour.RED);
        given(carsRepository.findByRegNumber(regNumber)).willReturn(Mono.just(car));

        Mono<Car> returned = carsController.getByRegNumber(regNumber);

        StepVerifier.create(returned)
                .expectNext(car)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldNotGetByInvalidRegNumber() {
        String regNumber = "AX33QIL";
        given(carsRepository.findByRegNumber(regNumber)).willReturn(Mono.empty());

        Mono<Car> returned = carsController.getByRegNumber(regNumber);

        StepVerifier.create(returned)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldCreate() {
        Car carToSave = new Car("QQ01YYY", "Subaru", Colour.RED);
        Car carSaved = new Car(1L, "QQ01YYY", "Subaru", Colour.RED);
        given(carsRepository.save(carToSave)).willReturn(Mono.just(carSaved));

        Mono<HttpResponse<Car>> response = carsController.create(carToSave);

        StepVerifier.create(response)
                .expectNextMatches(res -> res.code() == 201 && carSaved.equals(res.body()))
                .expectComplete()
                .verify();
    }
}
