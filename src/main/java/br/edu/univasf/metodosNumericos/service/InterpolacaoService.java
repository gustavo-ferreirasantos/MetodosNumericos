package br.edu.univasf.metodosNumericos.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterpolacaoService {

    // ================= EX1 =================
    // Regressão linear em log10(N) vs ano: retorna previsões para os anos informados pelo usuário (se quiser prever, passe anos adicionais)
    public Map<String, Object> preverLeiMoore(List<Double> anos, List<Double> nTrans) {
        Map<String, Object> out = new HashMap<>();

        if (anos.size() != nTrans.size() || anos.isEmpty()) {
            out.put("error", "Tamanhos diferentes ou vazios nos vetores anos e nTrans.");
            return out;
        }

        int n = anos.size();
        double sx = 0, sy = 0, sxy = 0, sx2 = 0;
        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            x[i] = anos.get(i);
            double nt = nTrans.get(i);
            if (nt <= 0) { // proteger log
                out.put("error", "Valores de nTrans devem ser positivos para aplicar log10.");
                return out;
            }
            y[i] = Math.log10(nt);
            sx += x[i];
            sy += y[i];
            sxy += x[i] * y[i];
            sx2 += x[i] * x[i];
        }

        double a = (n * sxy - sx * sy) / (n * sx2 - sx * sx);
        double b = (sy - a * sx) / n;

        out.put("a", a);
        out.put("b", b);

        // se o usuário quiser prever, pode enviar anos adicionais via front — aqui só devolvemos a reta e os dados originais
        List<Double> yPred = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double yp = Math.pow(10, a * x[i] + b);
            yPred.add(yp);
        }
        out.put("yPred", yPred);
        out.put("anos", anos);
        out.put("nTrans", nTrans);

        // também devolve função para previsões (log10)
        out.put("message", "Modelo log10(N) = a*ano + b. Use a e b para prever.");
        return out;
    }

    // ================= EX2 =================
    // Interpolação — devolve Lagrange e Newton para um valor
    public Map<String, Double> interpolar(List<Double> xiList, List<Double> viList, double valor) {
        Map<String, Double> out = new HashMap<>();
        if (xiList.size() != viList.size() || xiList.size() < 2) {
            out.put("error", Double.NaN);
            return out;
        }

        double[] xi = xiList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vi = viList.stream().mapToDouble(Double::doubleValue).toArray();

        int n = xi.length;
        // Escolher pontos centrados perto de "valor" para graus 2,3,4 se houver
        // Para simplicidade: usar os primeiros pontos suficientes (ou melhores próximos)
        // Implementação: construir comumente para graus 2,3,4 se existirem pontos
        if (n >= 3) {
            int[] idx2 = chooseCenteredIndices(xi, valor, 3);
            out.put("lagrange2", lagrange(xi, vi, valor, idx2));
            out.put("newton2", newtonInterp(xi, vi, valor, idx2));
        }
        if (n >= 4) {
            int[] idx3 = chooseCenteredIndices(xi, valor, 4);
            out.put("lagrange3", lagrange(xi, vi, valor, idx3));
            out.put("newton3", newtonInterp(xi, vi, valor, idx3));
        }
        if (n >= 5) {
            int[] idx4 = chooseCenteredIndices(xi, valor, 5);
            out.put("lagrange4", lagrange(xi, vi, valor, idx4));
            out.put("newton4", newtonInterp(xi, vi, valor, idx4));
        }

        return out;
    }

    // escolhe k índices centrados em torno do valor (ou os mais próximos)
    private int[] chooseCenteredIndices(double[] xi, double value, int k) {
        int n = xi.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, Comparator.comparingDouble(i -> Math.abs(xi[i] - value)));
        int[] res = new int[Math.min(k, n)];
        for (int i = 0; i < res.length; i++) res[i] = idx[i];
        Arrays.sort(res);
        return res;
    }

    private double lagrange(double[] xi, double[] yi, double x, int[] ids) {
        double soma = 0;
        for (int id : ids) {
            double termo = yi[id];
            for (int j : ids) {
                if (j == id) continue;
                termo *= (x - xi[j]) / (xi[id] - xi[j]);
            }
            soma += termo;
        }
        return soma;
    }

    private double newtonInterp(double[] xi, double[] yi, double x, int[] ids) {
        int m = ids.length;
        double[][] dd = new double[m][m];
        for (int i = 0; i < m; i++) dd[i][0] = yi[ids[i]];
        for (int j = 1; j < m; j++) {
            for (int i = 0; i < m - j; i++) {
                dd[i][j] = (dd[i + 1][j - 1] - dd[i][j - 1]) / (xi[ids[i + j]] - xi[ids[i]]);
            }
        }
        double soma = dd[0][0];
        double prod = 1;
        for (int j = 1; j < m; j++) {
            prod *= (x - xi[ids[j - 1]]);
            soma += dd[0][j] * prod;
        }
        return soma;
    }

    // ================= EX3 =================
    // Regressão — linear, quadrática, exponencial — retorna fórmulas e erros quadráticos
    public Map<String, Object> regressao(List<Double> xList, List<Double> fxList) {
        Map<String, Object> out = new HashMap<>();
        if (xList.size() != fxList.size() || xList.isEmpty()) {
            out.put("error", "Vetores com tamanhos diferentes ou vazios.");
            return out;
        }

        int n = xList.size();
        double[] x = xList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] fx = fxList.stream().mapToDouble(Double::doubleValue).toArray();

        // linear
        double[] lin = regressaoLinear(x, fx);
        out.put("linearA", lin[0]);
        out.put("linearB", lin[1]);
        out.put("erroLinear", lin[2]);

        // quadrática
        double[] quad = regressaoQuadratica(x, fx);
        out.put("quadA", quad[0]);
        out.put("quadB", quad[1]);
        out.put("quadC", quad[2]);
        out.put("erroQuadratica", quad[3]);

        // exponencial (fx>0)
        double[] expo = regressaoExponencial(x, fx);
        out.put("expoA", expo[0]);
        out.put("expoB", expo[1]);
        out.put("erroExponencial", expo[2]);

        return out;
    }

    private double[] regressaoLinear(double[] x, double[] y) {
        int n = x.length;
        double sx = 0, sy = 0, sxy = 0, sx2 = 0;
        for (int i = 0; i < n; i++) {
            sx += x[i];
            sy += y[i];
            sxy += x[i] * y[i];
            sx2 += x[i] * x[i];
        }
        double a = (n * sxy - sx * sy) / (n * sx2 - sx * sx);
        double b = (sy - a * sx) / n;
        double erro = 0;
        for (int i = 0; i < n; i++) erro += Math.pow(y[i] - (a * x[i] + b), 2);
        return new double[]{a, b, erro};
    }

    private double[] regressaoQuadratica(double[] x, double[] y) {
        int n = x.length;
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0, sy = 0, sxy = 0, sx2y = 0;
        for (int i = 0; i < n; i++) {
            sx += x[i];
            sx2 += x[i] * x[i];
            sx3 += Math.pow(x[i], 3);
            sx4 += Math.pow(x[i], 4);
            sy += y[i];
            sxy += x[i] * y[i];
            sx2y += x[i] * x[i] * y[i];
        }
        double[][] A = {
                {sx4, sx3, sx2},
                {sx3, sx2, sx},
                {sx2, sx, n}
        };
        double[] B = {sx2y, sxy, sy};
        double[] res = gauss(A, B);
        double erro = 0;
        for (int i = 0; i < n; i++) {
            double yi = res[0] * x[i] * x[i] + res[1] * x[i] + res[2];
            erro += Math.pow(y[i] - yi, 2);
        }
        return new double[]{res[0], res[1], res[2], erro};
    }

    private double[] regressaoExponencial(double[] x, double[] y) {
        int n = x.length;
        double sx = 0, sy = 0, sxy = 0, sx2 = 0;
        double[] lnY = new double[n];
        for (int i = 0; i < n; i++) {
            if (y[i] <= 0) {
                // não dá para fazer ln; retorna fit inválido com NaN
                return new double[]{Double.NaN, Double.NaN, Double.NaN};
            }
            lnY[i] = Math.log(y[i]);
        }
        for (int i = 0; i < n; i++) {
            sx += x[i];
            sy += lnY[i];
            sxy += x[i] * lnY[i];
            sx2 += x[i] * x[i];
        }
        double a1 = (n * sxy - sx * sy) / (n * sx2 - sx * sx);
        double b1 = (sy - a1 * sx) / n;
        double A = Math.exp(b1);
        double B = a1;
        double erro = 0;
        for (int i = 0; i < n; i++) {
            erro += Math.pow(y[i] - (A * Math.exp(B * x[i])), 2);
        }
        return new double[]{A, B, erro};
    }

    // eliminacao gauss (retorna x do Ax=b)
    private double[] gauss(double[][] A, double[] b) {
        int n = b.length;
        // cópia
        double[][] M = new double[n][n];
        double[] B = new double[n];
        for (int i = 0; i < n; i++) {
            B[i] = b[i];
            System.arraycopy(A[i], 0, M[i], 0, n);
        }
        for (int i = 0; i < n; i++) {
            // pivot simplificado
            for (int j = i + 1; j < n; j++) {
                double f = M[j][i] / M[i][i];
                for (int k = i; k < n; k++) M[j][k] -= M[i][k] * f;
                B[j] -= B[i] * f;
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = B[i];
            for (int j = i + 1; j < n; j++) x[i] -= M[i][j] * x[j];
            x[i] /= M[i][i];
        }
        return x;
    }
}
