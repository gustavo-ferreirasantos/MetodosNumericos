package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

@Service
public class MetodoIterativoService {

    public double[] resolver(double R1, double R2, double R3, double R4, double R5, double E) {

        double a11 = 1/R1 + 1/R2 + 1/R5;
        double a12 = -1/R1;
        double a13 = -1/R2;

        double a21 = -1/R1;
        double a22 = 1/R1 + 1/R3;
        double a23 = -1/R3;

        double a31 = -1/R2;
        double a32 = -1/R4;
        double a33 = 1/R2 + 1/R4;

        double[][] A = {
                {a11, a12, a13},
                {a21, a22, a23},
                {a31, a32, a33}
        };

        double[] b = {E / R1, 0, 0};

        return gaussSeidel(A, b, 0.00001, 500);
    }

    public double[] gaussSeidel(double[][] A, double[] b, double tol, int maxIter) {
        int n = b.length;
        double[] x = new double[n];
        double[] xOld = new double[n];

        for (int i = 0; i < n; i++) x[i] = b[i] / A[i][i];

        for (int iter = 0; iter < maxIter; iter++) {
            System.arraycopy(x, 0, xOld, 0, n);

            for (int i = 0; i < n; i++) {
                double soma = b[i];
                for (int j = 0; j < n; j++) {
                    if (i != j) soma -= A[i][j] * x[j];
                }
                x[i] = soma / A[i][i];
            }

            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (Math.abs(x[i] - xOld[i]) > tol) ok = false;
            }
            if (ok) break;
        }

        return x;
    }
}
