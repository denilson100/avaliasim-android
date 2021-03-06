package br.com.mobile10.avaliasim.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mobile10.avaliasim.R;

public class RecyclerViewHoldersFeatures extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtTitle;

    public RecyclerViewHoldersFeatures(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        txtTitle = (TextView)itemView.findViewById(R.id.title);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();

    }
}