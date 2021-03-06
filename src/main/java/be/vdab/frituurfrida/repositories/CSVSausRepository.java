package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;


//@PropertySource("application.properties")
@Component
@Qualifier("CSV")
public class CSVSausRepository implements SausRepository {
    private final Path pad;

    public CSVSausRepository(@Value("${CSVSausenPad}") Path pad) {
        this.pad = pad;
        System.out.println(pad);
    }

    @Override
    public Stream<Saus> findAll() {
        try{
            return Files.lines(pad).map(this::maakSaus);
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen " + pad, ex);
        }
    }


    public Saus maakSaus(String regel) {
        var onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            throw new SausRepositoryException(pad + " : " + regel + " : minder dan 2 onderdelen");
        }
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
            return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1], ingredienten);
        } catch (NumberFormatException ex) {
            throw new SausRepositoryException(pad + ": " + regel + " : verkeerde id", ex);
        }
    }
}
