package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@JdbcTest
@Import(SnackRepository.class)
@Sql("/insertSnacks.sql")
class SnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SNACKS = "snacks";
    private final SnackRepository repository;


    SnackRepositoryTest(SnackRepository repository) {
        this.repository = repository;
    }

    @Test
    void findAllGeeftAllePizzasGesorteerdOpId(){
        assertThat(repository.findAll()).hasSize(countRowsInTable(SNACKS))
                .extracting(Snack::getId)
                .isSorted();
    }
}