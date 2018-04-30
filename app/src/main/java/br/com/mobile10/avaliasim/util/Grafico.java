package br.com.mobile10.avaliasim.util;

import android.app.Activity;
import android.graphics.Color;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;

public class Grafico {

    private Activity activity;

    public Grafico(Activity activity) {
        this.activity = activity;
    }

    public void featureName(Avaliacao2 avaliacao, String nome) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(nome);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentagemFeaturePos(avaliacao, nome);
        float porcentagemNeg = Calculator.totalPorcentagemFeatureNeg(avaliacao, nome);
        // Caso de .5 para os dois, vai arredondar para cima e no final tera 101%
        if(porcentagemPos + porcentagemNeg > 100)
            porcentagemNeg = porcentagemNeg - 1;

        //ARGS-> (displayText, percentage, textColor, backgroundColor)
        data.add(new ChartData("" + porcentagemPos + "%", porcentagemPos, Color.WHITE, Color.parseColor("#4CAF50")));
        data.add(new ChartData("" + porcentagemNeg + "%", porcentagemNeg, Color.WHITE, Color.parseColor("#F44336")));
        pieChart.setChartData(data);
        pieChart.partitionWithPercent(true);
    }

    public void visaoGeral(Avaliacao2 avaliacao, String title) {
        List<ChartData> data = new ArrayList<>();

        //chart data with specified colors
        PieChart pieChart = (PieChart) activity.findViewById(R.id.pie_chart);
        pieChart.setAboutChart(title);
        data = new ArrayList<>();

        float porcentagemPos = Calculator.totalPorcentagemPositiva(avaliacao);
        float porcentagemNeg = Calculator.totalPorcentagemNegativo(avaliacao);
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
