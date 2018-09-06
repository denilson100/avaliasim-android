package br.com.mobile10.avaliasim.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */

public class RecyclerViewAdapterDetAvaliacoes extends RecyclerView.Adapter<RecyclerViewHoldersDetAvaliacoes> {

    private List<Feature> itemList;
    private Context context;
    //declare interface
    private OnItemClicked onClick;

    public RecyclerViewAdapterDetAvaliacoes(Context context, List<Feature> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    @Override
    public RecyclerViewHoldersDetAvaliacoes onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_det_avaliacoes, null);
        RecyclerViewHoldersDetAvaliacoes rcv = new RecyclerViewHoldersDetAvaliacoes(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoldersDetAvaliacoes holder, final int position) {
        int positive = 0;
        int negative = 0;
        String nomeFeature = itemList.get(position).name;
        for (MyDate date : itemList.get(position).date) {
            positive += date.positive;
            negative += date.negative;
        }

        holder.txtNomeFeature.setText(nomeFeature);
        holder.txtPositive.setText("" + positive);
        holder.txtNegative.setText("" + negative);
        holder.txtTotal.setText("Número de avaliações: " + (positive + negative));
//        holder.compareIndicator.updateView(10, 90);

//        holder.horizontal.init(context).addAll(itens()).build();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnClick(RecyclerViewAdapterDetAvaliacoes.OnItemClicked onClick) {
        this.onClick = onClick;
    }

}