package pl.jojczykp.micrud.controllers;

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

    private static final Car CAR_1 = new Car(1L, "XY01ABC", "Hyundai", Colour.RED);
    private static final Car CAR_2 = new Car(2L, "AB02XYZ", "Peugeot", Colour.BLUE);

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
        List<Car> cars = List.of(CAR_1, CAR_2);
        given(carsRepository.findAll()).willReturn(cars);

        Iterable<Car> returned = carsController.getAll();

        assertThat(returned).containsExactly(CAR_1, CAR_2);
    }

    @Test
    void shouldGetByRegNumber() {
        given(carsRepository.findByRegNumber(CAR_1.regNumber())).willReturn(Optional.of(CAR_1));

        Optional<Car> returned = carsController.getByRegNumber(CAR_1.regNumber());

        assertThat(returned).isPresent();
        assertThat(returned.get()).isEqualTo(CAR_1);
    }
}
