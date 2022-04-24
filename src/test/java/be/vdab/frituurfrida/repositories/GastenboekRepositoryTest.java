package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GastenboekRepository.class)
@Sql("/insertGastenBoek.sql")
class GastenboekRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String GASTENBOEK = "gastenboek";
    private final GastenboekRepository repository;

    public GastenboekRepositoryTest(GastenboekRepository repository) {
        this.repository = repository;
    }

    @Test
    void create() {
        var id = repository.create(new GastenboekEntry(0, "test3", LocalDate.now(), "test"));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(GASTENBOEK, "id = " + id)).isOne();
    }

    @Test
    void findAll() {
        assertThat(repository.findAll()).hasSize(countRowsInTable(GASTENBOEK)).extracting(GastenboekEntry::getDatum).isSortedAccordingTo(Comparator.reverseOrder());
    }

    private long idVanTestEntry() {
        return jdbcTemplate.queryForObject("select id from gastenboek where naam='test'", Long.class);
    }

    private long idVanTest2Entry() {
        return jdbcTemplate.queryForObject("select id from gastenboek where naam='test2'", Long.class);
    }

    @Test
    void delete() {
        var id = idVanTestEntry();
        var id2 = idVanTest2Entry();
        repository.delete(new Long[]{id, id2});
        assertThat(countRowsInTableWhere(GASTENBOEK, "id in (" + id + ',' + id2 + ')')).isZero();
    }

    @Test
    void deleteMetLegeArrayGeeftGeenException() {
        repository.delete(new Long[]{});
    }
}