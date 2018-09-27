package br.com.mobile10.avaliasim.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.presentation.activities.EvaluationDetailActivity;

public class RecyclerViewAdapterFeatures extends RecyclerView.Adapter<RecyclerViewHoldersFeatures> {

    private List<String> itemList;
    private Context context;
    private EvaluationDetailActivity activity;
    //declare interface
    private OnItemClicked onClick;

    public RecyclerViewAdapterFeatures(EvaluationDetailActivity activity, List<String> itemList) {
        this.itemList = itemList;
        this.activity = activity;

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClickFeature(int position);
    }

    @Override
    public RecyclerViewHoldersFeatures onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHoldersFeatures(
                MaterialRippleLayout.on(inflater.inflate(R.layout.item_list_features, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.2f)
                        .rippleColor(0xFF585858)
                        .rippleHover(true)
                        .create()
        );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoldersFeatures holder, final int position) {

        holder.txtTitle.setText(itemList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClickFeature(position);
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