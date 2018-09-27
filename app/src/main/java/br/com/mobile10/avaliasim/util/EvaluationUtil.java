package br.com.mobile10.avaliasim.util;

import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;

public class EvaluationUtil {
    public static String getTotoalDeAvaliacoes(Evaluation evaluation) {
        int positive = 0;
        int negative = 0;
        for (Feature feature : evaluation.getFeaturesList()) {
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
        }
        if (positive > 0 || negative > 0) {
            return "Número total de avaliações: " + (positive + negative);
        } else {
            return "Nenhuma avaliação";
        }
    }
}
