package br.com.mobile10.avaliasim.presentation.adapters;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.presentation.viewholders.DeliverableCardViewHolder;
import br.com.mobile10.avaliasim.presentation.viewholders.EvaluationCardViewHolder;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapterEvaluation extends RecyclerView.Adapter<EvaluationCardViewHolder> {

    private List<Evaluation> evaluations;

    public RecyclerViewAdapterEvaluation(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public EvaluationCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EvaluationCardViewHolder(
                MaterialRippleLayout.on(inflater.inflate(R.layout.evaluation_card_view_holder, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create());
    }

    @Override
    public void onBindViewHolder(EvaluationCardViewHolder holder, final int position) {
        Evaluation evaluation = evaluations.get(position);

        String features = "";
        for (String feature : evaluation.getFeatures()) {
            features += feature + "  ";
        }

        holder.setDeliverable(evaluation);
        holder.getTextFeature().setText(features);
        holder.getNameAuthor().setText(evaluation.getNameAuthor());
        UrlImageViewHelper.setUrlDrawable(holder.getPhotoAuthor(), evaluation.getPhotoAuthor(), R.drawable.ic_account_circle_black_24dp);
        Resources resources = holder.itemView.getResources();
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }
}