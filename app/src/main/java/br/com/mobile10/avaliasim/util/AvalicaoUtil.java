package br.com.mobile10.avaliasim.util;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.model.Avaliacao2;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;

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

    /**
     * Recebe uma avaliação e retorna uma lista de strings com os nomes das features.
     * @param avaliacao
     * @return
     */
    public static List<String> getListFeature(Avaliacao2 avaliacao) {
        List<String> list = new ArrayList<>();
        list.add("Média Geral");
        for (Feature feature : avaliacao.listaAvaliacoes) {
            list.add(feature.name);
        }
        return list;
    }
}
