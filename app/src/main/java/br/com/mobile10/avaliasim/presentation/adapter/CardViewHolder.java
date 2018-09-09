package br.com.mobile10.avaliasim.presentation.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Rating;
import br.com.mobile10.avaliasim.presentation.activity.RatingDetailsActivity;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {

    private Rating rating;
    private TextView cardTitle;
    private TextView ratingCreationDate;
    private ImageView cardDeliverableImage;

    CardViewHolder(View itemView) {
        super(itemView);
        cardTitle = itemView.findViewById(R.id.card_title);
        ratingCreationDate = itemView.findViewById(R.id.creation_date_text_view);
        cardDeliverableImage = itemView.findViewById(R.id.card_view_holder_img);
        itemView.setOnClickListener(this::onCardClick);
    }

    private void onCardClick(View v) {
        Intent intent = new Intent(v.getContext(), RatingDetailsActivity.class);
        intent.putExtra("rating", getRating());
        itemView.getContext().startActivity(intent);
    }

    public TextView getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(TextView cardTitle) {
        this.cardTitle = cardTitle;
    }

    public TextView getRatingCreationDate() {
        return ratingCreationDate;
    }

    public void setRatingCreationDate(TextView ratingCreationDate) {
        this.ratingCreationDate = ratingCreationDate;
    }

    public ImageView getCardDeliverableImage() {
        return cardDeliverableImage;
    }

    public void setCardDeliverableImage(ImageView cardDeliverableImage) {
        this.cardDeliverableImage = cardDeliverableImage;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}