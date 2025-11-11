package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.InterpolacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InterpolacaoController {

    @Autowired
    private InterpolacaoService service;

    @PostMapping("/interpolacao/lagrange")
    public String lagrange(
            @RequestParam double x1,@RequestParam double y1,
            @RequestParam double x2,@RequestParam double y2,
            @RequestParam double x3,@RequestParam double y3,
            @RequestParam double valor,
            Model model) {

        double[] x = {x1,x2,x3};
        double[] y = {y1,y2,y3};
        double res = service.interpolarLagrange(x,y,valor);
        model.addAttribute("resLag", res);
        model.addAttribute("active","interpolacao");
        return "index";
    }
}
