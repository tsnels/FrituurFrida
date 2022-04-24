package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class GastenboekRepository {

    private final SimpleJdbcInsert insert;
    private final JdbcTemplate template;

    public GastenboekRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template).withTableName("gastenboek")
                .usingGeneratedKeyColumns("id");
    }

    public long create(GastenboekEntry entry) {
        return insert.executeAndReturnKey(Map.of("naam", entry.getNaam(),
                "datum", entry.getDatum(), "bericht", entry.getBericht())).longValue();
    }

    private final RowMapper<GastenboekEntry> entryRowMapper = (result, rowNum) -> new GastenboekEntry(result.getLong("id"),
            result.getString("naam"),result.getObject("datum", LocalDate.class),
            result.getString("bericht"));

    public List<GastenboekEntry> findAll() {
        var sql = """
                select id, naam, datum, bericht
                from gastenboek
                order by datum desc
                """;
        return template.query(sql, entryRowMapper);
    }

    public void delete(Long[] ids) {
        if (ids.length != 0) {
            var sql = """
                        delete from gastenboek
                        where id in (
                    """
                    + "?,".repeat(ids.length - 1) + "?)";
            template.update(sql, ids);
        }
    }
}
