package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

@Service
public class IntegracaoService {

    public double trapezio(double[] x, double[] y) {
        double soma = 0;
        for (int i = 0; i < x.length - 1; i++) {
            soma += (x[i + 1] - x[i]) * (y[i] + y[i + 1]) / 2.0;
        }
        return soma;
    }

    public double simpson(double[] x, double[] y) {
        int n = x.length - 1;
        double h = (x[n] - x[0]) / n;
        double soma = y[0] + y[n];
        for (int i = 1; i < n; i++) {
            soma += y[i] * (i % 2 == 0 ? 2 : 4);
        }
        return soma * h / 3.0;
    }
}
