package be.vdab.frituurfrida.controllers;


import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sauzen")
class SausController {

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SausService sausService;

    SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", sausService.findAll().iterator());
    }

    @GetMapping("{id}")
    public ModelAndView findById(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        sausService.findById(id).ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", "alfabet")
                .addObject("sauzen", sausService.findByBeginNaam(letter).iterator());
    }
}
