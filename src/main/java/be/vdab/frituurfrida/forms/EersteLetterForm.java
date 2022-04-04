package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;

public record EersteLetterForm(@NotBlank String eerste) {
}