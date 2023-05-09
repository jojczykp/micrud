package pl.jojczykp.micrud.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;
import pl.jojczykp.micrud.repositories.CarsRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        given(carsRepository.findAll()).willReturn(List.of(car1, car2));

        Iterable<Car> returned = carsController.getAll();

        assertThat(returned).containsExactly(car1, car2);
    }

    @Test
    void shouldGetByRegNumber() {
        String regNumber = "FP77RFV";
        Car car = new Car(1L, regNumber, "Škoda", Colour.RED);
        given(carsRepository.findByRegNumber(regNumber)).willReturn(Optional.of(car));

        Optional<Car> returned = carsController.getByRegNumber(regNumber);

        assertThat(returned).isPresent();
        assertThat(returned.get()).isEqualTo(car);
    }

    @Test
    void shouldCreate() {
        Car carToSave = new Car("QQ01YYY", "Subaru", Colour.RED);
        Car carSaved = new Car(1L, "QQ01YYY", "Subaru", Colour.RED);
        given(carsRepository.save(carToSave)).willReturn(carSaved);

        HttpResponse<Car> response = carsController.post(carToSave);

        assertThat(response.code()).isEqualTo(201);
        assertThat(response.body()).isEqualTo(carSaved);
    }
}
