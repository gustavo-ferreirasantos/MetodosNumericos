package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.InterpolacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/interpolacaoPolinominal")
public class InterpolacaoController {

    private final InterpolacaoService service;

    public InterpolacaoController(InterpolacaoService service) {
        this.service = service;
    }

    // ---------- EXEMPLO 1 ----------
    @GetMapping("/ex1")
    public String ex1() {
        return "interpolacaoPolinominalEx1"; // sem pasta
    }

    @PostMapping("/ex1")
    public String calcularEx1(
            @RequestParam double ano1,
            @RequestParam double ano2,
            Model model
    ) {
        model.addAttribute("resultado", service.preverLeiMoore(ano1, ano2));
        return "interpolacaoPolinominalEx1"; // sem pasta
    }

    // ---------- EXEMPLO 2 ----------
    @GetMapping("/ex2")
    public String ex2() {
        return "interpolacaoPolinominalEx2";
    }

    @PostMapping("/ex2")
    public String calcularEx2(
            @RequestParam double valor,
            Model model
    ) {
        model.addAttribute("resultado", service.interpolar(valor));
        return "interpolacaoPolinominalEx2";
    }

    // ---------- EXEMPLO 3 ----------
    @GetMapping("/ex3")
    public String ex3() {
        return "interpolacaoPolinominalEx3";
    }

    @PostMapping("/ex3")
    public String calcularEx3(Model model) {
        model.addAttribute("resultado", service.regressao());
        return "interpolacaoPolinominalEx3";
    }
}
