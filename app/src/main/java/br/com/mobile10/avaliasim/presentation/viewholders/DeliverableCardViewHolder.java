package br.com.mobile10.avaliasim.presentation.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.presentation.activities.DeliverableDetailsActivity;

public class DeliverableCardViewHolder extends RecyclerView.ViewHolder {

    private Deliverable deliverable;
    private TextView cardTitle;
    private ImageView cardDeliverableImage;

    public DeliverableCardViewHolder(View itemView) {
        super(itemView);
        cardTitle = itemView.findViewById(R.id.card_title);
        cardDeliverableImage = itemView.findViewById(R.id.card_view_holder_img);
        itemView.setOnClickListener(this::onCardClick);
    }

    private void onCardClick(View v) {
        Intent intent = new Intent(v.getContext(), DeliverableDetailsActivity.class);
        intent.putExtra("deliverable", deliverable);
        itemView.getContext().startActivity(intent);
    }

    public TextView getCardTitle() {
        return cardTitle;
    }

    public ImageView getCardDeliverableImage() {
        return cardDeliverableImage;
    }

    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }
}