//package br.edu.univasf.metodosNumericos.controller;
//
//import br.edu.univasf.metodosNumericos.service.InterpolacaoService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/interpolacao")
//public class InterpolacaoController {
//
//    private final InterpolacaoService service;
//
//    public InterpolacaoController(InterpolacaoService service) {
//        this.service = service;
//    }
//
//    @GetMapping("")
//    public String index() {
//        return "topico3/menu";
//    }
//
//    // --------- EXEMPLO 1: LEI DE MOORE ---------
//    @GetMapping("/leiMoore")
//    public String leiMoore() {
//        return "topico3/leiMoore";
//    }
//
//    @PostMapping("/leiMoore")
//    public String calcularLeiMoore(@RequestParam double ano1,
//                                   @RequestParam double ano2,
//                                   Model model) {
//
//        var resultado = service.preverLeiMoore(ano1, ano2);
//        model.addAttribute("resultado", resultado);
//
//        return "topico3/leiMoore";
//    }
//
//
//    // --------- EXEMPLO 2: INTERPOLAÇÃO ---------
//    @GetMapping("/interpolacao")
//    public String interpolacao() {
//        return "topico3/interpolacao";
//    }
//
//    @PostMapping("/interpolacao")
//    public String calcularInterpolacao(@RequestParam double valor,
//                                       Model model) {
//
//        var resultado = service.interpolar(valor);
//        model.addAttribute("resultado", resultado);
//
//        return "topico3/interpolacao";
//    }
//
//
//    // --------- EXEMPLO 3: REGRESSÃO ---------
//    @GetMapping("/regressao")
//    public String regressao() {
//        return "topico3/regressao";
//    }
//
//    @PostMapping("/regressao")
//    public String calcularRegressao(Model model) {
//
//        var resultado = service.regressao();
//        model.addAttribute("resultado", resultado);
//
//        return "topico3/regressao";
//    }
//}
