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
        model.addAttribute("resGauss", x);
        return "metodosDiretos";
    }


    /*
    @PostMapping("/gauss")
    public String gauss(@RequestParam Map<String, String> params, Model model) {

        // Descobrir o tamanho n (baseado em quantos 'b' existem)
        int n = 0;
        for (String key : params.keySet()) {
            if (key.startsWith("b")) n++;
        }

        // Matriz aumentada (n x (n+1))
        double[][] a = new double[n + 1][n + 2]; // índice começa em 1 para compatibilidade com a função
        double temp;

        // Montagem da matriz aumentada [A | b]
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = Double.parseDouble(params.get("a" + (i - 1) + "_" + (j - 1)));
            }
            a[i][n + 1] = Double.parseDouble(params.get("b" + (i - 1)));
        }

        // Chama o método resolverSistema
        double[] x = service.resolverSistema(a, n);

        // Adiciona o resultado ao modelo
        model.addAttribute("resGauss", x);
        return "metodosDiretos";
    }
    */



}
