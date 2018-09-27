package br.com.mobile10.avaliasim.util;

import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;

public class Calculator {


    /* ------------------------ Media geral ---------------------------------- */

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     *
     * @param evaluation
     * @return
     */
    public static float totalPorcentagemPositiva(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : evaluation.getFeaturesList()) {
            int positive = 0;
            int negative = 0;
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
            totalPos += positive;
            totalNeg += negative;
        }
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     *
     * @param evaluation
     * @return
     */
    public static float totalPorcentagemNegativo(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : evaluation.getFeaturesList()) {
            int positive = 0;
            int negative = 0;
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
            totalPos += positive;
            totalNeg += negative;
        }
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }

    /* ------------------------ Media geral com data ---------------------------------- */

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     *
     * @param evaluation
     * @return
     */
    public static float totalPorcentagemPositivaUnicDate(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            int positive = 0;
//            int negative = 0;
//            for (MyDate date : feature.date) {
//                if (date.date.equals(Constants.DATE_UNIC)) {
//                    positive += date.positive;
//                    negative += date.negative;
//                }
//            }
//            totalPos += positive;
//            totalNeg += negative;
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     *
     * @param evaluation
     * @return
     */
    public static float totalPorcentagemNegativoUnicDate(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            int positive = 0;
//            int negative = 0;
//            for (MyDate date : feature.date) {
//                if (date.date.equals(Constants.DATE_UNIC)) {
//                    positive += date.positive;
//                    negative += date.negative;
//                }
//            }
//            totalPos += positive;
//            totalNeg += negative;
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }


    /* ------------------------ Feature com data uncia ---------------------------------- */

    public static float totalPorcentFeaturePositiveUnicDate(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            if (feature.name.equalsIgnoreCase(nomeFeature)) {
//                int positive = 0;
//                int negative = 0;
//                for (MyDate date : feature.date) {
//                    if (date.date.equals(Constants.DATE_UNIC)) {
//                        positive += date.positive;
//                        negative += date.negative;
//                    }
//                }
//                totalPos += positive;
//                totalNeg += negative;
//            }
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    public static float totalPorcentFeatureNegativeUnicDate(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            if (feature.name.equalsIgnoreCase(nomeFeature)) {
//                int positive = 0;
//                int negative = 0;
//                for (MyDate date : feature.date) {
//                    if (date.date.equals(Constants.DATE_UNIC)) {
//                        positive += date.positive;
//                        negative += date.negative;
//                    }
//                }
//                totalPos += positive;
//                totalNeg += negative;
//            }
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }


    /* ------------------------ Media geral com range da data ---------------------------------- */

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.
     *
     * @param evaluation
     * @return
     */
    public static float totalPorcentPositiveRangeDate(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            int positive = 0;
//            int negative = 0;
//            for (MyDate date : feature.date) {
//                if (!Constants.DATE1.after(date.date) && !Constants.DATE2.before(date.date)) {
//                    positive += date.positive;
//                    negative += date.negative;
//                }
//            }
//            totalPos += positive;
//            totalNeg += negative;
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    /**
     * Recebe uma avaliação, interar somando os valores pos e neg. Retorna o calculo da porcentagem.

     * @param evaluation
     * @return
     */
    public static float totalPorcentNegativeRangeDate(Evaluation evaluation) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            int positive = 0;
//            int negative = 0;
//            for (MyDate date : feature.date) {
//                if (!Constants.DATE1.after(date.date) && !Constants.DATE2.before(date.date)) {
//                    positive += date.positive;
//                    negative += date.negative;
//                }
//            }
//            totalPos += positive;
//            totalNeg += negative;
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }

    /* ------------------------ Feature com range de data ---------------------------------- */

    public static float totalPorcentFeaturePositiveRangeDate(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            if (feature.name.equalsIgnoreCase(nomeFeature)) {
//                int positive = 0;
//                int negative = 0;
//                for (MyDate date : feature.date) {
//                    if (!Constants.DATE1.after(date.date) && !Constants.DATE2.before(date.date)) {
//                        positive += date.positive;
//                        negative += date.negative;
//                    }
//                }
//                totalPos += positive;
//                totalNeg += negative;
//            }
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    public static float totalPorcentFeatureNegativeRangeDate(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

//        for (Feature feature : avaliacao.listaAvaliacoes) {
//            if (feature.name.equalsIgnoreCase(nomeFeature)) {
//                int positive = 0;
//                int negative = 0;
//                for (MyDate date : feature.date) {
//                    if (!Constants.DATE1.after(date.date) && !Constants.DATE2.before(date.date)) {
//                        positive += date.positive;
//                        negative += date.negative;
//                    }
//                }
//                totalPos += positive;
//                totalNeg += negative;
//            }
//        }
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }


    /* ---------------------------------------------------------- */

    /**
     * Calcula a porcentagem ja arredondando
     *
     * @param total
     * @param valorParaCalculo
     * @return
     */
    public static float calculaPorcentagem(float total, float valorParaCalculo) {
        float calc = (valorParaCalculo * 100) / total;
        int v = Math.round(calc);
        return v;
    }

    /* ---------------------------------------------------------- */

    public static float totalPorcentagemFeaturePos(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : evaluation.getFeaturesList()) {
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
        return calculaPorcentagem((totalPos + totalNeg), totalPos);
    }

    public static float totalPorcentagemFeatureNeg(Evaluation evaluation, String nomeFeature) {
        int totalPos = 0;
        int totalNeg = 0;

        for (Feature feature : evaluation.getFeaturesList()) {
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
        return calculaPorcentagem((totalPos + totalNeg), totalNeg);
    }

    /* ---------------------------------------------------------- */
}
