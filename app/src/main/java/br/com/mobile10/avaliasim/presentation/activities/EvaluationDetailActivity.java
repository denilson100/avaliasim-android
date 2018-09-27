package br.com.mobile10.avaliasim.presentation.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapterFeatures;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.Constants;
import br.com.mobile10.avaliasim.util.Grafico;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationDetailActivity extends AppCompatActivity {

    private RecyclerViewAdapterFeatures recyclerViewAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private RelativeLayout fundoDinamic;
    private Toolbar toolbar;

    private IEvaluationDAO evaluationDAO;
    private IUserDAO userDAO;
    private Evaluation evaluation;
    private List<String> features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation);
        initializeViews();
        evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
        evaluationDAO = new EvaluationDAO(evaluation.getId());
        evaluationDAO.findTotalEvaluation(evaluation, this::totalEvaluations);
        toolbar.setTitle(Constants.DELIVERABLE.getName());
        setSupportActionBar(toolbar);

        features = evaluation.getFeatures();
        features.add(0, "Média Geral");
        recyclerViewAdapter = new RecyclerViewAdapterFeatures(this, features);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter.setOnClick(this::onFeatureClick);

        Grafico grafico = new Grafico(this);
        grafico.visaoGeral(evaluation, "Média Geral");
    }

    private void initializeViews() {
        fundoDinamic = findViewById(R.id.fundo);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.fab).setOnClickListener(this::onEvaluationBtnClick);
    }

    private void onEvaluationBtnClick(View view) {
        AnimationsUtility.showCircularAnimationAvaliar(EvaluationDetailActivity.this, fundoDinamic, R.id.conteudo);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(EvaluationDetailActivity.this, AvaliandoActivity.class);
            intent.putExtra("evaluation", evaluation);
            startActivity(intent);
            finish();
        }, 1000);
    }

    private void totalEvaluations(Object o) {
        ((TextView) findViewById(R.id.total)).setText("" + o);
    }

    private void onFeatureClick(int position) {
        Grafico grafico = new Grafico(this);

        if (position == 0)
            grafico.visaoGeral(evaluation, features.get(position));
        else
            grafico.featureName(evaluation, features.get(position));
    }

    @Override
    protected void onStop() {
        super.onStop();
        AnimationsUtility.showCircularAnimationAvaliar(EvaluationDetailActivity.this, fundoDinamic, R.id.conteudo);
    }
}
