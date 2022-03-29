package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.dto.TotaleVerkopen;
import be.vdab.frituurfrida.domain.Snack;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class SnackRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    private final RowMapper<Snack> snackMapper = (result, rowNum) ->
            new Snack(result.getLong("id"), result.getString("naam"),
                    result.getBigDecimal("prijs"));

    private final RowMapper<BigDecimal> prijsMapper =
            (result, rowNum) -> result.getBigDecimal("prijs");


    private final RowMapper<TotaleVerkopen> sellsMapper = (result, rowNum) ->
            new TotaleVerkopen(result.getInt("id"), result.getString("naam"),
                    result.getInt("aantal"));



    public SnackRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template).withTableName("snacks").usingGeneratedKeyColumns("id");
    }

    public List<Snack> findAll() {
        var sql = """
                select id, naam, prijs
                from snacks
                order by id
                """;
        return template.query(sql, snackMapper);
    }

    public List<TotaleVerkopen> sells() {
        var sql = """
                select id, naam, sum(aantal) as aantal
                from snacks left outer join dagverkopen
                on snacks.id = dagverkopen.snackId
                group by id
                """;
        return template.query(sql, sellsMapper);
    }
}
