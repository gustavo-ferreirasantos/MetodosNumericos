package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

@Service
public class MetodoIterativoService {

    /**
     *       (E)
     *       / \
     *      R1  R2
     *     /     \
     *   (Vb)---R5----(Vc)
     *     \     /
     *      R4  R3
     *       \ /
     *      (GND)
     *
     * @param R1 Resistor 1
     * @param R2 Resistor 2
     * @param R3 Resistor 3
     * @param R4 Resistor 4
     * @param R5 Resistor 5
     * @param E  Tensão na fonte
     */
    public double[] resolver(double R1, double R2, double R3, double R4, double R5, double E) {

        // Análise nodal das equações:
        // Nodo Vb: (Vb - E)/R1 + Vb/R4 + (Vb - Vc)/R5 = 0  => Vb*(1/R1 + 1/R4 + 1/R5) - Vc*(1/R5) = E/R1
        // Nodo Vc: (Vc - E)/R2 + Vc/R3 + (Vc - Vb)/R5 = 0  => -Vb*(1/R5) + Vc*(1/R2 + 1/R3 + 1/R5) = E/R2

        double a11 = 1.0 / R1 + 1.0 / R4 + 1.0 / R5;
        double a12 = -1.0 / R5;
        double b1 = E / R1;

        double a21 = -1.0 / R5;
        double a22 = 1.0 / R2 + 1.0 / R3 + 1.0 / R5;
        double b2 = E / R2;

        double[][] A = {
                {a11, a12},
                {a21, a22}
        };

        double[] b = {b1, b2};

        return gaussSeidel(A, b, 0.00001, 500);
    }

    public double[] gaussSeidel(double[][] A, double[] b, double tol, int maxIter) {
        int n = b.length;
        double[] x = new double[n];
        double[] xOld = new double[n];


        for (int i = 0; i < n; i++) {
            x[i] = 0;
        }

        for (int iter = 0; iter < maxIter; iter++) {
            System.arraycopy(x, 0, xOld, 0, n);

            for (int i = 0; i < n; i++) {
                double soma = b[i];
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        soma -= A[i][j] * x[j];
                    }
                }
                x[i] = soma / A[i][i];
            }

            boolean converged = true;
            for (int i = 0; i < n; i++) {
                if (Math.abs(x[i] - xOld[i]) > tol) {
                    converged = false;
                    break;
                }
            }
            if (converged) {
                break;
            }
        }

        return x;
    }
}
