package br.edu.univasf.metodosNumericos.controller;

import br.edu.univasf.metodosNumericos.service.MetodoIterativoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MetodoIterativoController {

    private final MetodoIterativoService service;

    public MetodoIterativoController(MetodoIterativoService service) {
        this.service = service;
    }

    @GetMapping("/metodoIterativo")
    public String abrir() {
        return "metodosIterativos";
    }

    @PostMapping("/metodoIterativo")
    public String calcular(
            @RequestParam double R1,
            @RequestParam double R2,
            @RequestParam double R3,
            @RequestParam double R4,
            @RequestParam double R5,
            @RequestParam double E,
            Model model
    ) {

        double[] v = service.resolver(R1, R2, R3, R4, R5, E);

        double VA = v[0], VB = v[1], VD = v[2];

        model.addAttribute("I1", (VA - VB) / R1);
        model.addAttribute("I2", (VA - VD) / R2);
        model.addAttribute("I3", (VB - VD) / R3);
        model.addAttribute("I4", (VD - 0) / R4);
        model.addAttribute("I5", (VA - 0) / R5);

        return "metodosIterativos";
    }
}
