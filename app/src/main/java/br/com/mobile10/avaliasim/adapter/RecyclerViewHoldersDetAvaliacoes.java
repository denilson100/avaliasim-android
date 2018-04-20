package br.com.mobile10.avaliasim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.comparsechart.CompareIndicator;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import br.com.mobile10.avaliasim.R;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */

public class RecyclerViewHoldersDetAvaliacoes extends RecyclerView.ViewHolder implements View.OnClickListener{

//    public CompareIndicator compareIndicator;
    public TextView txtNomeFeature;
    public TextView txtPositive;
    public TextView txtNegative;
    public TextView txtTotal;

    public RecyclerViewHoldersDetAvaliacoes(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
//        compareIndicator = (CompareIndicator) itemView.findViewById(R.id.compare_indicator);
        txtNomeFeature = (TextView) itemView.findViewById(R.id.nome_feature);
        txtPositive = (TextView) itemView.findViewById(R.id.positive);
        txtNegative = (TextView) itemView.findViewById(R.id.negative);
        txtTotal = (TextView) itemView.findViewById(R.id.total);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
//        Variaveis.posicaoDaImagem = getPosition();

    }
}
