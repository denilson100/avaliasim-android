package br.com.mobile10.avaliasim.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Avaliacao2> itemList;
    private Context context;
    //declare interface
    private OnItemClicked onClick;

    public RecyclerViewAdapter(Context context, List<Avaliacao2> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {

        holder.txtTitle.setText(itemList.get(position).title);
        holder.txtType.setText(itemList.get(position).type);

        String texto = "";
        for (int i=0; i < itemList.get(position).features.size(); i++) {
            texto += itemList.get(position).features.get(i) + " \n";
        }
        holder.txtFeatures.setText(texto);

        Log.d("TAG", "ITEM LIST: " + itemList.size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}