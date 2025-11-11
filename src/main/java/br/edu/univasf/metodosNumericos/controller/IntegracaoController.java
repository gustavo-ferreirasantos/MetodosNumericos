package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IntegracaoController {

    @Autowired
    private IntegracaoService service;

    @GetMapping("/integracaoNumerica")
    public ModelAndView integracaoNumerica() {
        ModelAndView mv = new ModelAndView("integracaoNumerica");
        return mv;
    }


    @PostMapping("/integracao/calcular")
    public String calcular(
            @RequestParam double x1,@RequestParam double y1,
            @RequestParam double x2,@RequestParam double y2,
            @RequestParam double x3,@RequestParam double y3,
            Model model) {

        double[] x = {x1,x2,x3};
        double[] y = {y1,y2,y3};
        double trap = service.trapezio(x,y);
        double simp = service.simpson(x,y);
        model.addAttribute("resTrap", trap);
        model.addAttribute("resSimp", simp);
        model.addAttribute("active","integracao");
        return "index";
    }
}
