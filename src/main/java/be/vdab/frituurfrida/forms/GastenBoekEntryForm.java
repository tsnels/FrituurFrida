package be.vdab.frituurfrida.forms;

import be.vdab.frituurfrida.domain.GastenboekEntry;

import java.time.LocalDate;

public class GastenBoekEntryForm extends GastenboekEntry {

    public GastenBoekEntryForm(String naam, String bericht) {
        super(0, naam, LocalDate.now(), bericht);
    }
}
