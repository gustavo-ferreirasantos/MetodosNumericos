package br.edu.univasf.metodosNumericos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping({"/","/index"})
    public ModelAndView index(Model model) {
        return new ModelAndView("home");
    }
}
