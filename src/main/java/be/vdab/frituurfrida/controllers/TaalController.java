package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;


@Controller
@RequestMapping("taal")

public class TaalController {

    @GetMapping
    public ModelAndView nederlands(@RequestHeader("Accept-Language") String language) {
        return new ModelAndView("taal", "nederlands", language.startsWith("nl"));
    }
}

