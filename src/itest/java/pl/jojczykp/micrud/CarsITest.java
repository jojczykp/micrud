package pl.jojczykp.micrud;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;
import pl.jojczykp.micrud.repositories.CarsRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class CarsITest {

    @Client("/cars")
    interface CarsClient {
        @Get
        List<Car> getAll();

        @Get("/{regNumber}")
        Optional<Car> getByRegNumber(String regNumber);

        @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
        Car create(@Body Car car);
    }

    @BeforeEach
    void deleteAllCars(CarsRepository carsRepository) {
        carsRepository.deleteAll();
    }

    @Test
    void shouldCreateAndFindByRegNumber(CarsClient carsClient) {
        Car request = new Car("AA11AAA", "Fiat", Colour.YELLOW);

        Car created = carsClient.create(request);
        assertThat(created).usingRecursiveComparison().ignoringFields("id").isEqualTo(request);
        assertThat(created.id()).isNotNull();

        Optional<Car> foundByRegNumber = carsClient.getByRegNumber(request.regNumber());
        assertThat(foundByRegNumber).isPresent();
        assertThat(foundByRegNumber.get()).isEqualTo(created);
    }

    @Test
    void shouldCreateAndGetAll(CarsClient carsClient) {
        Car car1 = new Car("BB11BBB", "Pontiac", Colour.RED);
        Car car2 = new Car("CC11CCC", "Dacia", Colour.GREEN);
        Car car3 = new Car("DD11DDD", "Seat", Colour.BLUE);

        carsClient.create(car1);
        carsClient.create(car2);
        carsClient.create(car3);

        List<Car> allCars = carsClient.getAll();

        assertThat(allCars).allMatch(car -> nonNull(car.id()));
        assertThat(allCars)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(car1, car2, car3);
    }
}
