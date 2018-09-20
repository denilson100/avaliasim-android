package br.com.mobile10.avaliasim.presentation.adapter;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Deliverable> deliverables;

    public RecyclerViewAdapter(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardViewHolder cardViewHolder = new CardViewHolder(
                MaterialRippleLayout.on(inflater.inflate(R.layout.card_view_holder, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create());
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        Deliverable deliverable = deliverables.get(position);

        holder.getCardTitle().setText(deliverable.getName());
        Resources resources = holder.itemView.getResources();
        holder.getCardDeliverableImage().setImageResource(resources.getIdentifier("product".equals(deliverable.getType()) ? "ic_type_product" : "ic_type_service", "drawable", holder.itemView.getContext().getPackageName()));
        holder.setDeliverable(deliverable);

//        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.teste);
//        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return deliverables.size();
    }
}