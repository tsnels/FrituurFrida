package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.forms.GastenBoekEntryForm;
import be.vdab.frituurfrida.services.GastenBoekService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("gastenboek")
public class GastenBoekController {

    private final GastenBoekService gastenBoekService;

    public GastenBoekController(GastenBoekService gastenBoekService) {
        this.gastenBoekService = gastenBoekService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("gastenboek", "gastenboekEntries", gastenBoekService.findAll());
    }

    @GetMapping("toevoegen/form")
    public ModelAndView toevoegForm() {
        return new ModelAndView("gastenboek", "gastenboekEntries",
                gastenBoekService.findAll()).addObject(new GastenBoekEntryForm("", ""));
    }

    @PostMapping("toevoegen")
    public ModelAndView toevoegen(@Valid GastenBoekEntryForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("gastenboek", "gastenboekEntries", gastenBoekService.findAll());
        }
        gastenBoekService.create(form);
        return new ModelAndView("redirect:/gastenboek");
    }

    @PostMapping("verwijderen")
    public String delete(Optional<Long[]> id) {
        id.ifPresent(ids -> gastenBoekService.delete(ids));
        return "redirect:/gastenboek";
    }
}
