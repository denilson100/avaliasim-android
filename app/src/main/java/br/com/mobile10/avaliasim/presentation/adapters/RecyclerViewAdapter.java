package br.com.mobile10.avaliasim.presentation.adapters;

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
import br.com.mobile10.avaliasim.presentation.viewholders.DeliverableCardViewHolder;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapter extends RecyclerView.Adapter<DeliverableCardViewHolder> {

    private List<Deliverable> deliverables;

    public RecyclerViewAdapter(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    @Override
    public DeliverableCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DeliverableCardViewHolder(
                MaterialRippleLayout.on(inflater.inflate(R.layout.deliverable_card_view_holder, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create());
    }

    @Override
    public void onBindViewHolder(DeliverableCardViewHolder holder, final int position) {
        Deliverable deliverable = deliverables.get(position);

        holder.setDeliverable(deliverable);
        holder.getCardTitle().setText(deliverable.getName());
        Resources resources = holder.itemView.getResources();
        holder.getCardDeliverableImage().setImageResource(resources.getIdentifier("product".equals(deliverable.getType()) ? "ic_type_product" : "ic_type_service", "drawable", holder.itemView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return deliverables.size();
    }
}