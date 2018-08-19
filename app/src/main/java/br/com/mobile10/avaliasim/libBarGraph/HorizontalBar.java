package br.com.mobile10.avaliasim.libBarGraph;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.libBarGraph.adapter.BarItemRecycleViewAdapter;
import br.com.mobile10.avaliasim.libBarGraph.interfaces.OnItemClickListener;
import br.com.mobile10.avaliasim.libBarGraph.model.BarItem;


/**
 * Created by user on 16/01/2018.
 */

public class HorizontalBar extends LinearLayout {

    private OnItemClickListener listener;
    private Context context;
    private List<BarItem> items;
    private RecyclerView recyclerView;

    public HorizontalBar(Context context) {
        super(context);
        init(context);
    }

    public HorizontalBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalBar(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HorizontalBar init(Context context) {
        this.context = context;
        return this;
    }

    public HorizontalBar add(BarItem items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.add(items);
        notifyList();
        return this;
    }

    private void notifyList() {
        if (this.recyclerView != null && this.recyclerView.getAdapter() != null) {
            this.recyclerView.setAdapter(new BarItemRecycleViewAdapter(items, listener));
        }
    }
}
