package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

@Service
public class IntegracaoService {

    /*
    0 10 20 30 40
    50.8 86.2 136 72.8 51
    113.6 144.5 185  171.2 95.3
    */


    public double[] calcularArea(double[] x, double[] m1, double[] m2) {

        int n = x.length;

        if (n < 2) {
            throw new IllegalArgumentException("É necessário pelo menos 2 pontos.");
        }

        // ETAPA 1 — Construir f(x): largura = M2(x) - M1(x)

        double[] largura = new double[n];
        for (int i = 0; i < n; i++) {
            largura[i] = m2[i] - m1[i];
        }


        // ETAPA 2 — Calcular h = distância entre pontos (todos igualmente espaçados)

        double h = x[1] - x[0];


        //ETAPA 3 — Aplicar Regra dos Trapézios Repetida
        //Fórmula:
        // A = h/2 * [ f0 + 2(f1 + f2 + ... + f_n-1) + f_n ]

        double somaTrapezio = largura[0] + largura[n - 1];

        for (int i = 1; i < n - 1; i++) {
            somaTrapezio += 2 * largura[i];
        }

        double areaTrapezio = (h / 2.0) * somaTrapezio;



        // ETAPA 4 — Aplicar Regra de Simpson 1/3 Repetida

        //Fórmula:
        //A = h/3 * [ f0 + fn + 4(f1 + f3 + f5 + ... ) + 2(f2 + f4 + f6 + ... ) ]

        if ((n - 1) % 2 != 0) {
            throw new IllegalArgumentException(
                    "Simpson requer número PAR de subintervalos (n-1 deve ser par)."
            );
        }

        double somaImpares = 0;
        double somaPares = 0;

        for (int i = 1; i < n - 1; i++) {
            if (i % 2 == 1) {        // índices ímpares
                somaImpares += largura[i];
            } else {                // índices pares
                somaPares += largura[i];
            }
        }

        double areaSimpson = (h / 3.0) * (largura[0] + largura[n - 1] + 4 * somaImpares + 2 * somaPares);



        return new double[]{ areaTrapezio, areaSimpson };
        // Retorna [Trapézio, Simpson]
    }
}
