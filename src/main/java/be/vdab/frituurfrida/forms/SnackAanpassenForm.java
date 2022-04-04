package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record SnackAanpassenForm(@NotBlank String naam, @NotNull BigDecimal prijs) {
}
