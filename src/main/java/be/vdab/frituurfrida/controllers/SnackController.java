package be.vdab.frituurfrida.controllers;


import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import be.vdab.frituurfrida.forms.EersteLetterForm;
import be.vdab.frituurfrida.forms.SnackAanpassenForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;

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

//    @GetMapping("{id}/form")
//    public ModelAndView snackAanpassenForm(@PathVariable long id) {
//        var modelAndView = new ModelAndView("snack").addObject(new SnackAanpassenForm("", BigDecimal.ZERO));
//        snackService.findById(id).ifPresent(snack -> {
//            modelAndView.addObject("snack", snack);
//    });
//    return modelAndView;
//    }

    @GetMapping("{id}/wijzigen/form")
    public ModelAndView snackAanpassenForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("snack");
        snackService.findById(id).ifPresent(snack ->
            modelAndView.addObject(snack));
        return modelAndView;
    }

//    @PostMapping("{id}")
//    public ModelAndView findById(@Valid SnackAanpassenForm form, @PathVariable long id, Errors errors) {
//        var modelAndView = new ModelAndView("snack");
//        snackService.findById(id).ifPresent(snack -> {
//            modelAndView.addObject("snack", snack);
//            modelAndView.addObject("naam");
//        });
//        if(! modelAndView.isEmpty()) {
//            snackService.update(id, form.naam(), form.prijs());
//        }
//            return modelAndView;
//    }


        @PostMapping("wijzigen")
    public String findById(@Valid Snack snack, Errors errors, RedirectAttributes redirect) {
            if (errors.hasErrors()) {
                return "snack";
            }
            try {
                snackService.update(snack);
                return "redirect:/";
            } catch (SnackNietGevondenException ex) {
                redirect.addAttribute("snackNietGevonden", snack.getId());
                return "redirect:/";
            }
        }


        @GetMapping("test")
    public ModelAndView update(){
//        snackService.updateTest(new Snack(1, "Fricandell", BigDecimal.TEN));
        return new ModelAndView("test");
        }
    }