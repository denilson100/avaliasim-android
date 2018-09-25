package br.com.mobile10.avaliasim.presentation.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.presentation.activities.EvaluationsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EvaluationCardViewHolder extends RecyclerView.ViewHolder {

    private Evaluation evaluation;
    private TextView nameAuthor;
    private TextView textFeature;
    private CircleImageView imageViewphotoAuthor;

    public EvaluationCardViewHolder(View itemView) {
        super(itemView);
        nameAuthor = itemView.findViewById(R.id.name_author);
        textFeature = itemView.findViewById(R.id.features);
        imageViewphotoAuthor = itemView.findViewById(R.id.photo_author);
        itemView.setOnClickListener(this::onCardClick);
    }

    private void onCardClick(View v) {
        // Vai para detailsEvaluation
        Intent intent = new Intent(v.getContext(), EvaluationsActivity.class);
        intent.putExtra("evaluation", evaluation);
//        itemView.getContext().startActivity(intent);
    }

    public TextView getNameAuthor() {
        return nameAuthor;
    }

    public TextView getTextFeature() {
        return textFeature;
    }

    public CircleImageView getPhotoAuthor() {
        return imageViewphotoAuthor;
    }

    public void setDeliverable(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}