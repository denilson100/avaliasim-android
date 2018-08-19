package br.com.mobile10.avaliasim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mobile10.avaliasim.R;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtTitle;
    public TextView txtFeatures;
    public ImageView imageType;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        txtTitle = (TextView)itemView.findViewById(R.id.title);
        txtFeatures = (TextView)itemView.findViewById(R.id.features);
        imageType = (ImageView) itemView.findViewById(R.id.imageType);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
//        Variaveis.posicaoDaImagem = getPosition();

    }
}
