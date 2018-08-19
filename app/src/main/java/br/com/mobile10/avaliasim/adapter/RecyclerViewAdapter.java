package br.com.mobile10.avaliasim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.mobile10.avaliasim.R;
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

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHolders(
                MaterialRippleLayout.on(inflater.inflate(R.layout.item_list_event_card, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create()
        );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {

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

        switch (itemList.get(position).type) {
            case "Produto":
                holder.imageType.setImageResource(R.drawable.ic_type_product);
                break;
            case "Serviço":
                holder.imageType.setImageResource(R.drawable.ic_type_service);
                break;
            default:
                holder.imageType.setImageResource(R.drawable.ic_not_interested_black_24dp);

        }

        int lastPosition = -1;
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.teste);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClick.onItemClick(position);
                }
                catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
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