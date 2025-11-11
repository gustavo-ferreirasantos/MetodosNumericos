package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.MetodoIterativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MetodoIterativoController {

    @Autowired
    private MetodoIterativoService service;

    @PostMapping("/metodo-iterativo/gauss-seidel")
    public String gaussSeidel(
            @RequestParam double a11, @RequestParam double a12, @RequestParam double a13,
            @RequestParam double a21, @RequestParam double a22, @RequestParam double a23,
            @RequestParam double a31, @RequestParam double a32, @RequestParam double a33,
            @RequestParam double b1, @RequestParam double b2, @RequestParam double b3,
            @RequestParam double tol, @RequestParam int maxIter,
            Model model) {

        double[][] A = {{a11,a12,a13},{a21,a22,a23},{a31,a32,a33}};
        double[] b = {b1,b2,b3};
        double[] x = service.gaussSeidel(A,b,tol,maxIter);
        model.addAttribute("resGS", x);
        model.addAttribute("active","iterativo");
        return "index";
    }
}
