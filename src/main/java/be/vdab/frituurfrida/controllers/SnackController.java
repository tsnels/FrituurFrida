package be.vdab.frituurfrida.controllers;


import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}