package pl.jojczykp.micrud;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class MicronautGraalvmCrudTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void shouldApplicationRun() {
        Assertions.assertTrue(application.isRunning());
    }

}
