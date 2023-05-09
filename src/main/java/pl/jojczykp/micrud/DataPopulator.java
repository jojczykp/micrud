package pl.jojczykp.micrud;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import pl.jojczykp.micrud.model.Car;
import pl.jojczykp.micrud.model.Colour;
import pl.jojczykp.micrud.repositories.CarsRepository;

import javax.transaction.Transactional;

import java.util.List;

import static io.micronaut.context.env.Environment.TEST;

@Singleton
@Requires(notEnv = TEST)
public class DataPopulator {

    private final CarsRepository carsRepository;

    public DataPopulator(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @EventListener
    @Transactional
    void init(StartupEvent event) {
        if (carsRepository.count() == 0) {
            carsRepository.saveAll(List.of(
                    new Car("SE01QPA", "Rolls Royce", Colour.RED),
                    new Car("AB11XYZ", "Aston Martin", Colour.GREEN),
                    new Car("YZ99ABC", "Bentley", Colour.BLUE)
            ));
        }
    }
}
