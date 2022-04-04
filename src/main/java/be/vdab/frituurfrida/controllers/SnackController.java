package be.vdab.frituurfrida.controllers;


import be.vdab.frituurfrida.forms.EersteLetterForm;
import be.vdab.frituurfrida.forms.SnackAanpassenForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("snacks")
public class SnackController {

    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping
    public ModelAndView findAll() {
//        ModelAndView model = new ModelAndView("snacks", "alleSnacks", SnackService.findAll());
        return new ModelAndView("snacks", "alleSnacks", snackService.findAll());
    }

    @GetMapping("totaleVerkopen")
    public ModelAndView verkocht() {
        return new ModelAndView("totaleVerkopen", "alleVerkopen", snackService.sells());
    }

    @GetMapping("eersteletter/form")
    public ModelAndView eersteLetterForm() {
        return new ModelAndView("eersteletter").addObject(new EersteLetterForm(""));
    }

    @GetMapping("eersteletter")
    public ModelAndView findByEersteLetter(@Valid EersteLetterForm form, Errors errors) {
        var modelAndView = new ModelAndView("eersteletter");
        if(errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("snacks",
                snackService.findByEersteLetter(form.eerste()));
    }

    @GetMapping("{id}")
    public ModelAndView findById(@Valid SnackAanpassenForm form, @PathVariable long id, Errors errors) {
        var modelAndView = new ModelAndView("snack");
        snackService.findById(id).ifPresent(snack -> {
            modelAndView.addObject("snack", snack);
            modelAndView.addObject("naam");
        });
        return modelAndView;
    }
}