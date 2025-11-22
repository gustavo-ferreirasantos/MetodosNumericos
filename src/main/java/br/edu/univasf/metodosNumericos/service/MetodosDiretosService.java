package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Scanner;

@Service
public class MetodosDiretosService {

    public double[] resolverGauss(double[][] A, double[] b) {
        int n = b.length;

        // Cria cópias das matrizes A e b para evitar alterar os dados originais
        double[][] M = new double[n][n];
        double[] B = new double[n];
        for (int i = 0; i < n; i++) {
            B[i] = b[i];
            for (int j = 0; j < n; j++) M[i][j] = A[i][j];
        }

        // ===============================
        // ETAPA 1: ELIMINAÇÃO PROGRESSIVA
        // ===============================
        for (int k = 0; k < n - 1; k++) {

            // --- Pivotamento parcial ---
            // Encontra a linha com o maior valor absoluto na coluna k
            // e a move para o topo (para melhorar estabilidade numérica)
            int piv = k;
            double max = Math.abs(M[k][k]);
            for (int r = k + 1; r < n; r++) {
                if (Math.abs(M[r][k]) > max) {
                    max = Math.abs(M[r][k]);
                    piv = r;
                }
            }

            // Se a linha pivô for diferente da atual, troca as linhas
            if (piv != k) {
                double[] tmp = M[k];
                M[k] = M[piv];
                M[piv] = tmp;

                double t = B[k];
                B[k] = B[piv];
                B[piv] = t;
            }

            // --- Eliminação abaixo da diagonal ---
            // Zera todos os elementos abaixo do pivô (M[i][k] = 0)
            for (int i = k + 1; i < n; i++) {
                double fator = M[i][k] / M[k][k];

                // Atualiza a linha i da matriz M
                for (int j = k; j < n; j++) {
                    M[i][j] -= fator * M[k][j];
                }

                // Atualiza o vetor B
                B[i] -= fator * B[k];
            }
        }

        // =================================
        // ETAPA 2: SUBSTITUIÇÃO RETROATIVA
        // =================================
        double[] x = new double[n];

        // Resolve o sistema triangular superior
        for (int i = n - 1; i >= 0; i--) {
            double soma = 0;
            for (int j = i + 1; j < n; j++) {
                soma += M[i][j] * x[j]; // soma dos termos já conhecidos
            }

            // Calcula o valor de x[i]
            x[i] = (B[i] - soma) / M[i][i];
        }

        // Retorna o vetor solução
        return x;
    }



    // Função que resolve o sistema linear usando Eliminação de Gauss
    public double[] resolverSistema(double[][] a, int n) {
        double[] x = new double[n + 1];
        double temp;

        // Triangularização
        for (int k = 1; k < n; k++) {
            for (int i = k + 1; i <= n; i++) {
                temp = (-1.0) * a[i][k] / a[k][k];
                for (int j = 1; j <= n + 1; j++) {
                    a[i][j] = (temp * a[k][j]) + a[i][j];
                }
            }
        }

        // Substituição retroativa
        for (int i = n; i >= 1; i--) {
            x[i] = a[i][n + 1];
            for (int j = n; j > i; j--) {
                x[i] -= x[j] * a[i][j];
            }
            x[i] = x[i] / a[i][i];
        }

        return x;
    }

    private String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(number);
    }

    public String formatarEquacoes(double[][] a, double[] b) {
        StringBuilder sb = new StringBuilder();
        int n = b.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(formatNumber(a[i][j])).append(" X").append(j + 1);
                if (j < n - 1) {
                    sb.append(" + ");
                }
            }
            sb.append(" = ").append(formatNumber(b[i])).append("<br>");
        }
        return sb.toString();
    }

    public String formatarResultado(double[] x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            sb.append("X").append(i + 1).append(" = ").append(formatNumber(x[i])).append("<br>");
        }
        return sb.toString();
    }
}
