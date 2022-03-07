package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;


@Controller
class IndexController {

    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;

//    public ModelAndView index() {
//        var dag = LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY ? "gesloten." : "geopend.";
//        var model = new ModelAndView("index", "dagVanDeWeek", dag);
//        model.addObject("adres", new Adres("Gelderhorsten", 101, new Gemeente("Lommel", 3920)));
//        return model;
//    }

    @GetMapping("/")

    public ModelAndView index(@CookieValue Optional<Integer> aantalBezoeken, HttpServletResponse response) {
            var dag = LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY ? "gesloten." : "geopend.";
            var modelAndView = new ModelAndView("index", "dagVanDeWeek", dag);
            modelAndView.addObject("adres", new Adres("Gelderhorsten", 101,
                    new Gemeente("Lommel", 3920)));
        var nieuwAantalBezoeken = aantalBezoeken.orElse(0) + 1;
        var cookie = new Cookie("aantalBezoeken", String.valueOf(nieuwAantalBezoeken));
        cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
        cookie.setPath("/");
        response.addCookie(cookie);
        modelAndView.addObject("aantalBezoeken", nieuwAantalBezoeken);
        return modelAndView;
    }
}