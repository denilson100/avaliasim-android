package br.com.mobile10.avaliasim.util;

import android.app.Activity;
import android.graphics.Color;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Evaluation;

public class Grafico {

    private Activity activity;

    public Grafico(Activity activity) {
        this.activity = activity;
    }

    /**
     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada.
     * @param evaluation
     * @param nome
     */
    public void featureName(Evaluation evaluation, String nome) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(nome);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentagemFeaturePos(evaluation, nome);
        float porcentagemNeg = Calculator.totalPorcentagemFeatureNeg(evaluation, nome);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }

    /**
     * Recebe uma avaliacao e seta um grafico geral com a media de todas as features
     * @param evaluation
     * @param title
     */
    public void visaoGeral(Evaluation evaluation, String title) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(title);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentagemPositiva(evaluation);
        float porcentagemNeg = Calculator.totalPorcentagemNegativo(evaluation);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }


    /**
     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para uma unica data.
     * @param avaliacao
     */
    public void visaoGeralUnicDate(Evaluation avaliacao, String title) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(title);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentagemPositivaUnicDate(avaliacao);
        float porcentagemNeg = Calculator.totalPorcentagemNegativoUnicDate(avaliacao);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }


    /**
     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para uma unica data.
     * @param avaliacao
     * @param nome
     */
    public void featureNameUnicDate(Evaluation avaliacao, String nome) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(nome);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentFeaturePositiveUnicDate(avaliacao, nome);
        float porcentagemNeg = Calculator.totalPorcentFeatureNegativeUnicDate(avaliacao, nome);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }

    /**
     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para um range de data.
     * @param avaliacao
     */
    public void visaoGeralRangeDate(Evaluation avaliacao, String title) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(title);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentPositiveRangeDate(avaliacao);
        float porcentagemNeg = Calculator.totalPorcentNegativeRangeDate(avaliacao);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }


    /**
     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para um range de data.
     * @param avaliacao
     * @param nome
     */
    public void featureNameRangeDate(Evaluation avaliacao, String nome) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(nome);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentFeaturePositiveRangeDate(avaliacao, nome);
        float porcentagemNeg = Calculator.totalPorcentFeatureNegativeRangeDate(avaliacao, nome);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }
}
