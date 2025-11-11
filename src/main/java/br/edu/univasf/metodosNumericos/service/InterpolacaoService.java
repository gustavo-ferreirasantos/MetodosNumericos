package br.edu.univasf.metodosNumericos.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

@Service
public class InterpolacaoService {

    public double interpolarLagrange(double[] x, double[] y, double valor) {
        double resultado = 0;
        int n = x.length;
        for (int i = 0; i < n; i++) {
            double termo = y[i];
            for (int j = 0; j < n; j++) {
                if (j != i) termo *= (valor - x[j]) / (x[i] - x[j]);
            }
            resultado += termo;
        }
        return resultado;
    }

    public double[] regressaoLinear(double[] x, double[] y) {
        SimpleRegression reg = new SimpleRegression();
        for (int i = 0; i < x.length; i++) reg.addData(x[i], y[i]);
        return new double[]{reg.getIntercept(), reg.getSlope()};
    }
}
