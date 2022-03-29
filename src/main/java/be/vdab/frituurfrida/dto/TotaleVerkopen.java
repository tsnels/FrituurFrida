package be.vdab.frituurfrida.dto;

public class TotaleVerkopen {

    private final long id;
    private final String naam;
    private final int aantal;

    public TotaleVerkopen(int id, String naam, int aantal) {
        this.id = id;
        this.naam = naam;
        this.aantal = aantal;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantal() {
        return aantal;
    }
}
