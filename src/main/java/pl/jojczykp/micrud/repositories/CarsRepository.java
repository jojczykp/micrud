package pl.jojczykp.micrud.repositories;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import pl.jojczykp.micrud.model.Car;
import reactor.core.publisher.Mono;

@JdbcRepository(dialect = Dialect.H2)
public interface CarsRepository extends ReactorCrudRepository<Car, Long> {

    Mono<Car> findByRegNumber(String regNumber);
}
