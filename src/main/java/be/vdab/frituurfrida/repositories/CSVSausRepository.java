package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class CSVSausRepository {
    private static final Path PAD = Paths.get("/data/sauzen.csv");

    public Stream<Saus> findAll() {
        try{
            return Files.lines(PAD).map(this::maakSaus);
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen " + PAD, ex);
        }
    }

    private Saus maakSaus(String regel) {
        var onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            throw new SausRepositoryException(PAD + " : " + regel + " : minder dan 2 onderdelen");
        }
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
            return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1], ingredienten);
        } catch (NumberFormatException ex) {
            throw new SausRepositoryException(PAD + ": " + regel + " : verkeerde id", ex);
        }
    }
}
