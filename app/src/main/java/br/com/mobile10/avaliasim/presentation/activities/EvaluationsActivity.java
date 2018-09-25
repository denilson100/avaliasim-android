package br.com.mobile10.avaliasim.presentation.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ViewSwitcher;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.DeliverableDAO;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.dao.RatingDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IClockDAO;
import br.com.mobile10.avaliasim.data.interfaces.IDeliverableDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IRatingDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapterEvaluation;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationsActivity extends AppCompatActivity {

    private IUserDAO userDAO;
    private IRatingDAO ratingDAO;
    private CoordinatorLayout coordinatorLayout;
    private Deliverable deliverable;

    private SwipeRefreshLayout swipeLayout;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;

    private IClockDAO clockDAO;
    private IEvaluationDAO evaluationDAO;

    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();

        deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        userDAO = new UserDAO();

        toolbarLayout.setTitle(deliverable.getName());

        evaluationDAO = new EvaluationDAO(deliverable.getId());
        evaluationDAO.findAll(this::loadEvaluationsCards);
//        clockDAO.synchronizeClock();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeViews() {
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        recyclerView = findViewById(R.id.cards_recycler_view);
        viewSwitcher = findViewById(R.id.cards_switcher);
        swipeLayout = findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void onRefreshSwipe() {
        evaluationDAO.findAll(result -> recyclerView.setAdapter(new RecyclerViewAdapterEvaluation((List<Evaluation>) result)));
        swipeLayout.setRefreshing(false);
    }

    public void loadEvaluationsCards(Object result) {
        List<Evaluation> evaluations = (List<Evaluation>) result;
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapterEvaluation(evaluations));
//        viewSwitcher.showNext();
    }
}
