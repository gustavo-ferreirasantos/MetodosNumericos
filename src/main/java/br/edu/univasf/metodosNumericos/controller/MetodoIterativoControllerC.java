//package br.edu.univasf.metodosNumericos.controller;
//
//import br.edu.univasf.metodosNumericos.service.MetodoIterativoService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Map;
//
//@Controller
//public class MetodoIterativoControllerC {
//
//    private final MetodoIterativoService service;
//
//    public MetodoIterativoControllerC(MetodoIterativoService service) {
//        this.service = service;
//    }
//
//    @GetMapping("/metodoIterativo")
//    public String abrirTela(Model model) {
//        return "metodosIterativos";
//    }
//
//    @PostMapping("/metodoIterativo")
//    public String resolver(
//            @RequestParam int n,
//            @RequestParam Map<String, String> params,
//            Model model
//    ) {
//        double[][] A = new double[n][n];
//        double[] b = new double[n];
//
//        // recebe valores digitados
//        for (int i = 0; i < n; i++) {
//            b[i] = Double.parseDouble(params.get("b" + i));
//
//            for (int j = 0; j < n; j++) {
//                A[i][j] = Double.parseDouble(params.get("a" + i + "_" + j));
//            }
//        }
//
//        double[] resultado = service.resolver(A, b, 0.0001, 500);
//
//        model.addAttribute("res", resultado);
//        model.addAttribute("n", n);
//
//        return "metodoIterativo";
//    }
//}
