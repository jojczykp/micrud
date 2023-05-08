package pl.jojczykp.micrud.repositories;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import pl.jojczykp.micrud.model.Car;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2)
public interface CarsRepository extends CrudRepository<Car, Long> {

    Optional<Car> findByRegNumber(String regNumber);
}
