package pl.jojczykp.micrud.repositories;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class CarsRepositoryTest {

    public static final Car CAR_1 = new Car("PQ12BBC", "Bugatti", Colour.YELLOW);
    public static final Car CAR_2 = new Car("ST12UWZ", "Mercedes", Colour.BLUE);

    @Inject
    CarsRepository carsRepository;

    @Test
    void shouldSaveSingleCar() {
        carsRepository.save(CAR_1);

        Iterable<Car> saved = carsRepository.findAll();
        assertThat(saved).allMatch(hasId());
        assertThat(saved)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(CAR_1);
    }

    @Test
    void shouldSaveMultipleCars() {
        carsRepository.saveAll(List.of(CAR_1, CAR_2));

        Iterable<Car> saved = (carsRepository.findAll());
        assertThat(saved).allMatch(hasId());
        assertThat(saved)
                .asList().usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(CAR_1, CAR_2);
    }

    @Test
    void shouldFindByRegNumber() {
        String regNumber = "AB01CDE";
        Car saved = new Car(regNumber, "Honda", Colour.PINK);
        carsRepository.save(saved);

        Optional<Car> maybeFound = carsRepository.findByRegNumber(regNumber);

        assertThat(maybeFound).isPresent();
        assertThat(maybeFound.get())
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(saved);
    }

    @Test
    void shouldNotFindByRegNumber() {
        Car saved = new Car("UW67QWE", "Mazda", Colour.BROWN);
        carsRepository.save(saved);

        Optional<Car> maybeFound = carsRepository.findByRegNumber("ANOTHER");

        assertThat(maybeFound).isNotPresent();
    }

    private static Predicate<Car> hasId() {
        return car -> nonNull(car.id());
    }
}
