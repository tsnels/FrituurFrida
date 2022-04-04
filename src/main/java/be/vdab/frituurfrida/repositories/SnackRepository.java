package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.dto.TotaleVerkopen;
import be.vdab.frituurfrida.domain.Snack;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

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

    public List<Snack> findByEersteLetter(String eersteLetter) {
        var sql = """
                select id, naam, prijs
                from snacks
                where naam like ?
                """;
        return template.query(sql, snackMapper, eersteLetter+'%');
    }

    public Optional<Snack> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs
                    from snacks
                    where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
//
//    public List<Snack> findById(int id) {
//        var sql = """
//                select id, naam, prijs
//                from snacks
//                where id = ?
//                """;
//        return template.query(sql, snackMapper, id);
//    }


}
