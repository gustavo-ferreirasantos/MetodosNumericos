//package br.edu.univasf.metodosNumericos.service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class MetodoIterativoServiceCopia {
//
//    public double[] resolver(double[][] A, double[] b, double tolerancia, int maxIter) {
//
//        int n = A.length;
//        double[] x = new double[n];
//        double[] xAnt = new double[n];
//
//        // aproximação inicial bi / aii
//        for (int i = 0; i < n; i++) {
//            x[i] = b[i] / A[i][i];
//        }
//
//        for (int iter = 0; iter < maxIter; iter++) {
//
//            System.arraycopy(x, 0, xAnt, 0, n);
//
//            for (int i = 0; i < n; i++) {
//                double soma = b[i];
//
//                for (int j = 0; j < n; j++) {
//                    if (j != i) soma -= A[i][j] * x[j];
//                }
//
//                x[i] = soma / A[i][i];
//            }
//
//            // convergência
//            if (convergiu(x, xAnt, tolerancia)) {
//                return x;
//            }
//        }
//
//        return x;
//    }
//
//    private boolean convergiu(double[] x, double[] xAnt, double tol) {
//        for (int i = 0; i < x.length; i++) {
//            if (Math.abs(x[i] - xAnt[i]) > tol)
//                return false;
//        }
//        return true;
//    }
//}
