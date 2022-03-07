package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

@RequestMapping("sauzen")
@Controller
public class SauzenController {
    private final Saus[] alleSauzen = {
            new Saus(1, "cocktail", new String[]{"mayonaise", "ketchup", "cognac"}),
            new Saus(2, "mayonaise", new String[]{"ei", "mosterd"}),
            new Saus(3, "mosterd", new String[]{"mosterd", "azijn", "witte wijn"}),
            new Saus(4, "tartare", new String[]{"mayonaise", "augurk", "tabasco"})};

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", alleSauzen);
    }

    private Optional<Saus> findByIdHelper(long id) {
        return Arrays.stream(alleSauzen).filter(saus -> saus.getId()==id).findFirst();
    }

    @GetMapping("{id}")
    public ModelAndView findById(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        findByIdHelper(id).ifPresent(saus -> modelAndView.addObject("saus", saus));
        return modelAndView;
    }
}