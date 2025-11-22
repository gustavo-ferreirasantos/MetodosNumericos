package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.InterpolacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/interpolacaoPolinominal")
public class InterpolacaoController {

    private final InterpolacaoService service;

    public InterpolacaoController(InterpolacaoService service) {
        this.service = service;
    }


    // ============================================================
    // EXEMPLO 1 – LEI DE MOORE (Regressão Logarítmica)
    // ============================================================

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
        Map<String, Object> r = service.preverLeiMoore(anos, nTrans);

        if (r.containsKey("error")) {
            model.addAttribute("resultado", "Erro: " + r.get("error"));
            return "interpolacaoPolinominalEx1";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Lei de Moore – Ajuste Logarítmico\n");
        sb.append("--------------------------------------------------------\n\n");

        sb.append("Modelo ajustado:\n");
        sb.append(String.format("log10(N) = %.6f · ano + %.6f\n\n", r.get("a"), r.get("b")));

        sb.append("Previsões calculadas para os anos fornecidos:\n");
        List<Double> ypred = (List<Double>) r.get("yPred");
        for (int i = 0; i < anos.size(); i++) {
            sb.append(String.format("Ano %.0f → N = %.4e transistores\n", anos.get(i), ypred.get(i)));
        }

        sb.append("\nDados informados:\n");
        List<Double> nT = (List<Double>) r.get("nTrans");
        for (int i = 0; i < anos.size(); i++) {
            sb.append(String.format("Ano %.0f → N real = %.0f\n", anos.get(i), nT.get(i)));
        }

        model.addAttribute("resultado", sb.toString());
        return "interpolacaoPolinominalEx1";
    }



    // ============================================================
    // EXEMPLO 2 – Interpolação (Lagrange e Newton)
    // ============================================================

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
        Map<String, Double> r = service.interpolar(xi, vi, valor);

        if (r.containsKey("error") && Double.isNaN(r.get("error"))) {
            model.addAttribute("resultado", "Erro: pontos insuficientes.");
            return "interpolacaoPolinominalEx2";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Interpolação Polinomial – Lagrange & Newton\n");
        sb.append("--------------------------------------------------------\n\n");
        sb.append(String.format("Valor consultado: x = %.4f\n\n", valor));

        if (r.containsKey("lagrange2")) {
            sb.append(String.format("Grau 2:\n  Lagrange = %.6f\n  Newton = %.6f\n\n",
                    r.get("lagrange2"), r.get("newton2")));
        }

        if (r.containsKey("lagrange3")) {
            sb.append(String.format("Grau 3:\n  Lagrange = %.6f\n  Newton = %.6f\n\n",
                    r.get("lagrange3"), r.get("newton3")));
        }

        if (r.containsKey("lagrange4")) {
            sb.append(String.format("Grau 4:\n  Lagrange = %.6f\n  Newton = %.6f\n\n",
                    r.get("lagrange4"), r.get("newton4")));
        }

        sb.append("Pontos utilizados:\n");
        for (int i = 0; i < xi.size(); i++) {
            sb.append(String.format("x%d = %.4f   |   y%d = %.4f\n", i, xi.get(i), i, vi.get(i)));
        }

        model.addAttribute("resultado", sb.toString());
        return "interpolacaoPolinominalEx2";
    }



    // ============================================================
    // EXEMPLO 3 – Regressão (Linear, Quadrática e Exponencial)
    // ============================================================

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
        Map<String, Object> r = service.regressao(x, fx);

        if (r.containsKey("error")) {
            model.addAttribute("resultado", "Erro: " + r.get("error"));
            return "interpolacaoPolinominalEx3";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Regressão – Linear, Quadrática e Exponencial\n");
        sb.append("--------------------------------------------------------\n\n");

        // LINEAR
        sb.append("🔹 Modelo Linear: f(x) = a + b·x\n");
        sb.append(String.format("a = %.6f\n", r.get("linearA")));
        sb.append(String.format("b = %.6f\n", r.get("linearB")));
        sb.append(String.format("Erro Quadrático = %.6f\n\n", r.get("erroLinear")));

        // QUADRÁTICA
        sb.append("🔹 Modelo Quadrático: f(x) = a + b·x + c·x²\n");
        sb.append(String.format("a = %.6f\n", r.get("quadA")));
        sb.append(String.format("b = %.6f\n", r.get("quadB")));
        sb.append(String.format("c = %.6f\n", r.get("quadC")));
        sb.append(String.format("Erro Quadrático = %.6f\n\n", r.get("erroQuadratica")));

        // EXPONENCIAL
        sb.append("🔹 Modelo Exponencial: f(x) = a·e^(b·x)\n");
        sb.append(String.format("a = %.6f\n", r.get("expoA")));
        sb.append(String.format("b = %.6f\n", r.get("expoB")));
        sb.append(String.format("Erro Quadrático = %.6f\n\n", r.get("erroExponencial")));

        // DADOS
        sb.append("Pontos utilizados:\n");
        for (int i = 0; i < x.size(); i++) {
            sb.append(String.format("x%d = %.4f   |   f(x%d) = %.4f\n",
                    i, x.get(i), i, fx.get(i)));
        }

        model.addAttribute("resultado", sb.toString());
        return "interpolacaoPolinominalEx3";
    }
}
