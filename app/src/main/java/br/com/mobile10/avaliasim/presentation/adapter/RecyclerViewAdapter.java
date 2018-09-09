package br.com.mobile10.avaliasim.presentation.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Rating;


/**
 * Created by denilsonmonteiro on 03/10/16.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Rating> ratings;

    public RecyclerViewAdapter(List<Rating> ratings) {
        this.ratings = ratings;
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
        Rating rating = ratings.get(position);

        holder.getCardTitle().setText(rating.getTitle());
        holder.getCardDeliverableImage().setImageResource(holder.itemView.getResources().getIdentifier(rating.getDeliverable().getImageResource(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.setRating(rating);

        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(rating.getTimestamp()), ZoneId.systemDefault());
        String date = ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        holder.getRatingCreationDate().setText(date);

//        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.teste);
//        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }
}