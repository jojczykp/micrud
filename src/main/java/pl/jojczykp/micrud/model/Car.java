package pl.jojczykp.micrud.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity
public record Car(
        @GeneratedValue @Id @Nullable Long id,
        String regNumber,
        String make,
        Colour colour) {

    public Car(String regNumber, String make, Colour colour) {
        this(null, regNumber, make, colour);
    }
}
