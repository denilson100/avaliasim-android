package br.com.mobile10.avaliasim.util;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.libBarGraph.model.BarItem;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;

public class Calculator {

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     * @param avaliacao
     * @return
     */
    public static float totalPorcentagemPositiva(Avaliacao2 avaliacao) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : avaliacao.listaAvaliacoes) {
            int positive = 0;
            int negative = 0;
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
            totalPos += positive;
            totalNeg += negative;
        }
        return calculaPorcentagem( (totalPos + totalNeg), totalPos);
    }

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     * @param avaliacao
     * @return
     */
    public static float totalPorcentagemNegativo(Avaliacao2 avaliacao) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : avaliacao.listaAvaliacoes) {
            int positive = 0;
            int negative = 0;
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
            totalPos += positive;
            totalNeg += negative;
        }
        return calculaPorcentagem( (totalPos + totalNeg), totalNeg);
    }

    /**
     * Calcula a porcentagem ja arredondando
     * @param total
     * @param valorParaCalculo
     * @return
     */
    public static float calculaPorcentagem(float total, float valorParaCalculo) {
        float calc = (valorParaCalculo * 100) / total;
        int v = Math.round(calc);
        return  v;
    }

    public static float totalPorcentagemFeaturePos(Avaliacao2 avaliacao, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : avaliacao.listaAvaliacoes) {
            if (feature.name.equalsIgnoreCase(nomeFeature)) {
                int positive = 0;
                int negative = 0;
                for (MyDate date : feature.date) {
                    positive += date.positive;
                    negative += date.negative;
                }
                totalPos += positive;
                totalNeg += negative;
            }
        }
        return calculaPorcentagem( (totalPos + totalNeg), totalPos);
    }

    public static float totalPorcentagemFeatureNeg(Avaliacao2 avaliacao, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : avaliacao.listaAvaliacoes) {
            if (feature.name.equalsIgnoreCase(nomeFeature)) {
                int positive = 0;
                int negative = 0;
                for (MyDate date : feature.date) {
                    positive += date.positive;
                    negative += date.negative;
                }
                totalPos += positive;
                totalNeg += negative;
            }
        }
        return calculaPorcentagem( (totalPos + totalNeg), totalNeg);
    }
}
