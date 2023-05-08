package pl.jojczykp.micrud.repositories;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MicronautTest
class CarsRepositoryTest {

    @Inject
    CarsRepository carsRepository;

    @Test
    void shouldSaveSingleCar() {
        long startCount = carsRepository.count();

        carsRepository.save(new Car("PQ12BBC", "Bugatti", Colour.YELLOW));

        long endCount = carsRepository.count();

        assertThat(startCount).isEqualTo(0);
        assertThat(endCount).isEqualTo(1);
    }

    @Test
    void shouldSaveMultipleCars() {
        long startCount = carsRepository.count();

        carsRepository.saveAll(List.of(
                new Car("ST12UWZ", "Mercedes", Colour.BLUE),
                new Car("CD34EFG", "Volvo", Colour.GREEN)
        ));

        long endCount = carsRepository.count();

        assertThat(startCount).isEqualTo(0);
        assertThat(endCount).isEqualTo(2);
    }

    @Test
    void shouldFindByRegNumber() {
        String regNumber = "AB01CDE";
        Car saved = new Car(regNumber, "Honda", Colour.PINK);
        carsRepository.save(saved);

        Optional<Car> maybeFound = carsRepository.findByRegNumber(regNumber);

        assertThat(maybeFound).isPresent();
        assertThat(maybeFound.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(saved);
    }

    @Test
    void shouldNotFindByRegNumber() {
        Car saved = new Car("UW67QWE", "Mazda", Colour.BROWN);
        carsRepository.save(saved);

        Optional<Car> maybeFound = carsRepository.findByRegNumber("ANOTHER");

        assertThat(maybeFound).isNotPresent();
    }
}
