package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InterpolacaoService {

    // ===================== EXEMPLO 1 — LEI DE MOORE ======================
    public Map<String, Double> preverLeiMoore(double ano1, double ano2) {

        // anos reais
        double[] ano = {1971, 1972, 1974, 1978, 1982, 1985, 1989, 1993, 1997, 1999, 2000};
        double[] N =   {2300, 3500, 6000, 29000, 120000, 275000, 1180000, 3100000,
                7500000, 24000000, 42000000};

        // transformar em log10
        double[] y = new double[N.length];
        for (int i = 0; i < N.length; i++) y[i] = Math.log10(N[i]);

        // regressão linear: y = a*ano + b
        int n = ano.length;
        double somaX = 0, somaY = 0, somaXY = 0, somaX2 = 0;

        for (int i = 0; i < n; i++) {
            somaX += ano[i];
            somaY += y[i];
            somaXY += ano[i] * y[i];
            somaX2 += ano[i] * ano[i];
        }

        double a = (n*somaXY - somaX*somaY) / (n*somaX2 - somaX*somaX);
        double b = (somaY - a*somaX)/n;

        Map<String, Double> mapa = new HashMap<>();

        // previsão para os anos solicitados
        double y1 = a*ano1 + b;
        double y2 = a*ano2 + b;

        mapa.put("ano1", Math.pow(10, y1));
        mapa.put("ano2", Math.pow(10, y2));

        return mapa;
    }

    // ===================== EXEMPLO 2 — INTERPOLAÇÃO POLINOMIAL ======================

    private final double[] xi = {0.25, 0.75, 1.25, 1.5, 2.0};
    private final double[] vi = {-0.45, -0.60, 0.70, 1.88, 6.0};

    public Map<String, Double> interpolar(double valor) {
        Map<String, Double> mapa = new HashMap<>();

        // 2º grau
        mapa.put("lagrange2", lagrange(valor, 1, 2, 3));
        mapa.put("newton2", newton(valor, 1, 2, 3));

        // 3º grau
        mapa.put("lagrange3", lagrange(valor, 0, 1, 2, 3));
        mapa.put("newton3", newton(valor, 0, 1, 2, 3));

        // 4º grau
        mapa.put("lagrange4", lagrange(valor, 0, 1, 2, 3, 4));
        mapa.put("newton4", newton(valor, 0, 1, 2, 3, 4));

        return mapa;
    }

    private double lagrange(double x, int... ids) {
        double soma = 0;
        for (int i : ids) {
            double termo = vi[i];
            for (int j : ids) {
                if (i != j) termo *= (x - xi[j]) / (xi[i] - xi[j]);
            }
            soma += termo;
        }
        return soma;
    }

    private double newton(double x, int... ids) {
        int m = ids.length;
        double[][] dd = new double[m][m];

        for (int i = 0; i < m; i++) dd[i][0] = vi[ids[i]];

        for (int j = 1; j < m; j++) {
            for (int i = 0; i < m - j; i++) {
                dd[i][j] = (dd[i+1][j-1] - dd[i][j-1]) / (xi[ids[i+j]] - xi[ids[i]]);
            }
        }

        double soma = dd[0][0];
        double prod = 1;

        for (int j = 1; j < m; j++) {
            prod *= (x - xi[ids[j-1]]);
            soma += dd[0][j] * prod;
        }

        return soma;
    }


    // ===================== EXEMPLO 3 — REGRESSÃO ======================

    private final double[] x =  {0, 1.5, 2.6, 4.2, 6, 8.2, 10, 11.4};
    private final double[] fx = {18, 13, 11,  9, 6,  4,  2,   1};

    public Map<String, Object> regressao() {
        Map<String, Object> mapa = new HashMap<>();

        // --------- Ajuste linear ---------
        double[] r1 = regressaoLinear();
        mapa.put("linear", r1[0] + "x + " + r1[1]);
        mapa.put("erroLinear", r1[2]);

        // --------- Ajuste parabólico ---------
        double[] r2 = regressaoQuadratica();
        mapa.put("quadratica", r2[0] + "x² + " + r2[1] + "x + " + r2[2]);
        mapa.put("erroQuadratica", r2[3]);

        // --------- Ajuste exponencial ---------
        double[] r3 = regressaoExponencial();
        mapa.put("exponencial", r3[0] + " e^(" + r3[1] + "x)");
        mapa.put("erroExponencial", r3[2]);

        return mapa;
    }

    // ===== funções auxiliares (modelos prontos, sem enrolação) =====

    private double[] regressaoLinear() {
        int n = x.length;
        double sx=0, sy=0, sxy=0, sx2=0;

        for (int i=0;i<n;i++){
            sx+=x[i];
            sy+=fx[i];
            sxy+=x[i]*fx[i];
            sx2+=x[i]*x[i];
        }

        double a = (n*sxy - sx*sy)/(n*sx2 - sx*sx);
        double b = (sy - a*sx)/n;

        double erro = 0;
        for (int i=0;i<n;i++)
            erro += Math.pow(fx[i] - (a*x[i] + b),2);

        return new double[]{a,b,erro};
    }

    private double[] regressaoQuadratica() {
        int n = x.length;
        double sx=0, sx2=0, sx3=0, sx4=0, sy=0, sxy=0, sx2y=0;

        for(int i=0;i<n;i++){
            sx += x[i];
            sx2 += x[i]*x[i];
            sx3 += x[i]*x[i]*x[i];
            sx4 += Math.pow(x[i],4);
            sy += fx[i];
            sxy += x[i]*fx[i];
            sx2y += x[i]*x[i]*fx[i];
        }

        double[][] A = {
                {sx4, sx3, sx2},
                {sx3, sx2, sx},
                {sx2, sx,  n}
        };
        double[] B = {sx2y, sxy, sy};
        double[] res = gauss(A,B);

        double erro=0;
        for(int i=0;i<n;i++)
            erro += Math.pow(fx[i] - (res[0]*x[i]*x[i] + res[1]*x[i] + res[2]),2);

        return new double[]{res[0], res[1], res[2], erro};
    }

    private double[] regressaoExponencial() {
        int n = x.length;
        double sx=0, sy=0, sxy=0, sx2=0;

        double[] Yln = new double[n];
        for(int i=0;i<n;i++) Yln[i] = Math.log(fx[i]);

        for (int i=0;i<n;i++) {
            sx += x[i];
            sy += Yln[i];
            sxy += x[i] * Yln[i];
            sx2 += x[i]*x[i];
        }

        double a1 = (n*sxy - sx*sy)/(n*sx2 - sx*sx);
        double b1 = (sy - a1*sx)/n;

        double A = Math.exp(b1);
        double B = a1;

        double erro=0;
        for(int i=0;i<n;i++)
            erro += Math.pow(fx[i] - (A*Math.exp(B*x[i])),2);

        return new double[]{A,B,erro};
    }

    private double[] gauss(double[][] A, double[] b){
        int n=b.length;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                double f=A[j][i]/A[i][i];
                for(int k=i;k<n;k++) A[j][k]-=A[i][k]*f;
                b[j]-=b[i]*f;
            }
        }
        double[] x=new double[n];
        for(int i=n-1;i>=0;i--){
            x[i]=b[i];
            for(int j=i+1;j<n;j++) x[i]-=A[i][j]*x[j];
            x[i]/=A[i][i];
        }
        return x;
    }
}
