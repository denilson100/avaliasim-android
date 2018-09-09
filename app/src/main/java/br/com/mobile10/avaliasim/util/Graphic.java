//package br.com.mobile10.avaliasim.util;
//
//import android.app.Activity;
//import android.graphics.Color;
//
//import com.intrusoft.scatter.ChartData;
//import com.intrusoft.scatter.PieChart;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import br.com.mobile10.avaliasim.R;
//import br.com.mobile10.avaliasim.model.Avaliacao2;
//
//public class Graphic {
//
//    private Activity activity;
//
//    public Graphic(Activity activity) {
//        this.activity = activity;
//    }
//
//    /**
//     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada.
//     *
//     * @param avaliacao
//     * @param nome
//     */
//    public void featureName(Avaliacao2 avaliacao, String nome) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(nome);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentagemFeaturePos(avaliacao, nome);
//        float porcentagemNeg = Calculator.totalPorcentagemFeatureNeg(avaliacao, nome);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//    /**
//     * Recebe uma avaliacao e seta um grafico geral com a media de todas as features
//     *
//     * @param avaliacao
//     * @param title
//     */
//    public void visaoGeral(Avaliacao2 avaliacao, String title) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(title);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentagemPositiva(avaliacao);
//        float porcentagemNeg = Calculator.totalPorcentagemNegativo(avaliacao);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//
//    /**
//     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para uma unica data.
//     *
//     * @param avaliacao
//     */
//    public void visaoGeralUnicDate(Avaliacao2 avaliacao, String title) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(title);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentagemPositivaUnicDate(avaliacao);
//        float porcentagemNeg = Calculator.totalPorcentagemNegativoUnicDate(avaliacao);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//
//    /**
//     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para uma unica data.
//     *
//     * @param avaliacao
//     * @param nome
//     */
//    public void featureNameUnicDate(Avaliacao2 avaliacao, String nome) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(nome);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentFeaturePositiveUnicDate(avaliacao, nome);
//        float porcentagemNeg = Calculator.totalPorcentFeatureNegativeUnicDate(avaliacao, nome);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//    /**
//     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para um range de data.
//     *
//     * @param avaliacao
//     */
//    public void visaoGeralRangeDate(Avaliacao2 avaliacao, String title) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(title);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentPositiveRangeDate(avaliacao);
//        float porcentagemNeg = Calculator.totalPorcentNegativeRangeDate(avaliacao);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//
//    /**
//     * Recebe avaliação e nome da feature, mostra o grafico com a média geral da feature passada para um range de data.
//     *
//     * @param avaliacao
//     * @param nome
//     */
//    public void featureNameRangeDate(Avaliacao2 avaliacao, String nome) {
//        List<ChartData> data;
//
//        //chart data with specified colors
//        PieChart pieChart = activity.findViewById(R.id.pie_chart);
//        pieChart.setAboutChart(nome);
//        data = new ArrayList<>();
//
//        float porcentagemPos = Calculator.totalPorcentFeaturePositiveRangeDate(avaliacao, nome);
//        float porcentagemNeg = Calculator.totalPorcentFeatureNegativeRangeDate(avaliacao, nome);
//        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
//        if (porcentagemPos + porcentagemNeg > 100)
//            porcentagemNeg = porcentagemNeg - 1;
//
//        //ARGS-> (displayText, percentage, textColor, backgroundColor)
//        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
//        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
//        pieChart.setChartData(data);
//        pieChart.partitionWithPercent(true);
//    }
//
//}
