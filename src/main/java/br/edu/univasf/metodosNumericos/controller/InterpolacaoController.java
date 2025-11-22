package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.InterpolacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/interpolacaoPolinominal")
public class InterpolacaoController {

    private final InterpolacaoService service;

    public InterpolacaoController(InterpolacaoService service) {
        this.service = service;
    }

    // EX1 - Lei de Moore (anos x N)
    @GetMapping("/ex1")
    public String ex1() {
        return "interpolacaoPolinominalEx1";
    }

    @PostMapping("/ex1")
    public String calcularEx1(
            @RequestParam("anos") List<Double> anos,
            @RequestParam("nTrans") List<Double> nTrans,
            Model model
    ) {
        model.addAttribute("resultado", service.preverLeiMoore(anos, nTrans));
        return "interpolacaoPolinominalEx1";
    }

    // EX2 - Interpolação (xi x vi)
    @GetMapping("/ex2")
    public String ex2() {
        return "interpolacaoPolinominalEx2";
    }

    @PostMapping("/ex2")
    public String calcularEx2(
            @RequestParam("xi") List<Double> xi,
            @RequestParam("vi") List<Double> vi,
            @RequestParam("valor") double valor,
            Model model
    ) {
        model.addAttribute("resultado", service.interpolar(xi, vi, valor));
        return "interpolacaoPolinominalEx2";
    }

    // EX3 - Regressão (x x f(x))
    @GetMapping("/ex3")
    public String ex3() {
        return "interpolacaoPolinominalEx3";
    }

    @PostMapping("/ex3")
    public String calcularEx3(
            @RequestParam("x") List<Double> x,
            @RequestParam("fx") List<Double> fx,
            Model model
    ) {
        model.addAttribute("resultado", service.regressao(x, fx));
        return "interpolacaoPolinominalEx3";
    }
}
