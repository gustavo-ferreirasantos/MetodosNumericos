package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IntegracaoController {

    @Autowired
    private IntegracaoService service;

    @GetMapping("/integracaoNumerica")
    public String mostrarPagina(Model model) {
        return "integracaoNumerica";
    }

    @PostMapping("/integracaoNumerica")
    public String calcular(
            @RequestParam("x") String xStr,
            @RequestParam("m1") String m1Str,
            @RequestParam("m2") String m2Str,
            Model model
    ) {
        try {
            // manter campos preenchidos
            model.addAttribute("x", xStr);
            model.addAttribute("m1", m1Str);
            model.addAttribute("m2", m2Str);

            // converter textos
            double[] x = parseInput(xStr);
            double[] m1 = parseInput(m1Str);
            double[] m2 = parseInput(m2Str);

            // validar tamanhos
            if (x.length != m1.length || x.length != m2.length) {
                model.addAttribute("erro", "Os vetores devem ter o mesmo tamanho.");
                return "integracaoNumerica";
            }

            // calcular áreas
            double[] resultado = service.calcularArea(x, m1, m2);

            model.addAttribute("areaTrapFmt", String.format("%.2f", resultado[0]));
            model.addAttribute("areaSimpsonFmt", String.format("%.2f", resultado[1]));

            // calcular f(x) e converter para lista de strings
            List<String> fListaStr = new ArrayList<>();
            for (int i = 0; i < x.length; i++) {
                double fx = m2[i] - m1[i];
                fListaStr.add(String.format("%.2f", fx));
            }

            model.addAttribute("fLista", fListaStr);

            model.addAttribute("mensagem", "Cálculo realizado com sucesso!");

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao processar: " + e.getMessage());
        }

        return "integracaoNumerica";
    }

    private double[] parseInput(String texto) {
        String[] partes = texto.trim().split("\\s+");
        double[] valores = new double[partes.length];

        for (int i = 0; i < partes.length; i++) {
            valores[i] = Double.parseDouble(partes[i].replace(",", "."));
        }

        return valores;
    }
}
