package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

@Service
public class MetodoIterativoService {

    public double[] gaussSeidel(double[][] A, double[] b, double tol, int maxIter) {
        int n = b.length;
        double[] x = new double[n];
        for (int iter = 0; iter < maxIter; iter++) {
            double erro = 0;
            for (int i = 0; i < n; i++) {
                double soma = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) soma += A[i][j] * x[j];
                }
                double novo = (b[i] - soma) / A[i][i];
                erro += Math.abs(novo - x[i]);
                x[i] = novo;
            }
            if (erro < tol) break;
        }
        return x;
    }
}
