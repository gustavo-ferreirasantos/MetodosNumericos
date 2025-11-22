package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.MetodosDiretosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MetodosDiretosController {

    @Autowired
    private MetodosDiretosService service;

    @GetMapping("/metodosDiretos")
    public ModelAndView metodosDiretos(){
        ModelAndView mv = new ModelAndView("metodosDiretos");
        return mv;
    }


    @PostMapping("/gauss")
    public String gauss(@RequestParam Map<String, String> params, Model model) {

        // Descobrir o tamanho n
        int n = 0;
        for (String key : params.keySet()) {
            if (key.startsWith("b")) n++;
        }

        double[][] A = new double[n][n];
        double[] b = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = Double.parseDouble(params.get("a" + i + "_" + j));
            }
            b[i] = Double.parseDouble(params.get("b" + i));
        }

        double[] x = service.resolverGauss(A, b);

        String equacoes = service.formatarEquacoes(A, b);
        String resultadoFormatado = service.formatarResultado(x);

        model.addAttribute("equacoes", equacoes);
        model.addAttribute("resGauss", resultadoFormatado);
        return "metodosDiretos";
    }



}
