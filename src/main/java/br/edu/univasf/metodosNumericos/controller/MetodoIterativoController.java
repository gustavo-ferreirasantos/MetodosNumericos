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
    public String abrir(Model model) {
        model.addAttribute("R1", "");
        model.addAttribute("R2", "");
        model.addAttribute("R3", "");
        model.addAttribute("R4", "");
        model.addAttribute("R5", "");
        model.addAttribute("E", "");
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

        double Vb = v[0];
        double Vc = v[1];

        double i1 = (E - Vb) / R1;
        double i2 = (E - Vc) / R2;
        double i3 = Vc / R3;
        double i4 = Vb / R4;
        double i5 = (Vb - Vc) / R5;
        double i6 = i1 + i2;

        model.addAttribute("I1", i1);
        model.addAttribute("I2", i2);
        model.addAttribute("I3", i3);
        model.addAttribute("I4", i4);
        model.addAttribute("I5", i5);
        model.addAttribute("I6", i6);

        model.addAttribute("R1", R1);
        model.addAttribute("R2", R2);
        model.addAttribute("R3", R3);
        model.addAttribute("R4", R4);
        model.addAttribute("R5", R5);
        model.addAttribute("E", E);

        return "metodosIterativos";
    }
}
