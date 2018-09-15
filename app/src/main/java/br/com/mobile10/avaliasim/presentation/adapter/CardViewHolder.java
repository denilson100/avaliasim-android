package br.com.mobile10.avaliasim.presentation.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.presentation.activity.DeliverableDetailsActivity;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {

    private Deliverable deliverable;
    private TextView cardTitle;
    private ImageView cardDeliverableImage;

    CardViewHolder(View itemView) {
        super(itemView);
        cardTitle = itemView.findViewById(R.id.card_title);
        cardDeliverableImage = itemView.findViewById(R.id.card_view_holder_img);
        itemView.setOnClickListener(this::onCardClick);
    }

    private void onCardClick(View v) {
        Intent intent = new Intent(v.getContext(), DeliverableDetailsActivity.class);
        intent.putExtra("deliverable", getDeliverable());
        itemView.getContext().startActivity(intent);
    }

    public TextView getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(TextView cardTitle) {
        this.cardTitle = cardTitle;
    }

    public ImageView getCardDeliverableImage() {
        return cardDeliverableImage;
    }

    public void setCardDeliverableImage(ImageView cardDeliverableImage) {
        this.cardDeliverableImage = cardDeliverableImage;
    }

    public Deliverable getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }
}