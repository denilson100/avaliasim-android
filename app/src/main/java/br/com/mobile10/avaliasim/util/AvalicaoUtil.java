package br.com.mobile10.avaliasim.util;

import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;

/**
 * Created by denmont on 20/04/2018.
 */

public class AvalicaoUtil {
    public static String getTotoalDeAvaliacoes(Avaliacao2 avaliacao) {
        int positive = 0;
        int negative = 0;
        for (Feature feature : avaliacao.listaAvaliacoes) {
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
