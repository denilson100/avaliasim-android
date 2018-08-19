package br.com.mobile10.avaliasim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.fragments.FragmentPerfil;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */

public class RecyclerViewAdapterMyAvaliacoes extends RecyclerView.Adapter<RecyclerViewHoldersMyAvaliacoes> {

    private List<Avaliacao2> itemList;
    private FragmentPerfil fragmentPerfil;
    //declare interface
    private OnItemClicked onClick;

    public RecyclerViewAdapterMyAvaliacoes(FragmentPerfil fragmentPerfil, List<Avaliacao2> itemList) {
        this.itemList = itemList;
        this.fragmentPerfil = fragmentPerfil;

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    @Override
    public RecyclerViewHoldersMyAvaliacoes onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHoldersMyAvaliacoes(
                MaterialRippleLayout.on(inflater.inflate(R.layout.item_list_my_avaliacoes, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create()
        );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoldersMyAvaliacoes holder, final int position) {

        holder.txtTitle.setText(itemList.get(position).title);

        String texto = "";
        for (int i=0; i < itemList.get(position).features.size(); i++) {
            if(i == (itemList.get(position).features.size() - 1)) {
                texto += itemList.get(position).features.get(i);
            } else {
                texto += itemList.get(position).features.get(i) + "  ";
            }
        }
        holder.txtFeatures.setText(texto);


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