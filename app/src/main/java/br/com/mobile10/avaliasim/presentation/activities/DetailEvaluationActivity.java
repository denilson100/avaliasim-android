package br.com.mobile10.avaliasim.presentation.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.libBarGraph.model.BarItem;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;
import br.com.mobile10.avaliasim.presentation.adapters.RecyclerViewAdapterFeatures;
import br.com.mobile10.avaliasim.util.Alerts;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.Constants;
import br.com.mobile10.avaliasim.util.EvaluationUtil;
import br.com.mobile10.avaliasim.util.Grafico;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DetailEvaluationActivity extends AppCompatActivity implements RecyclerViewAdapterFeatures.OnItemClicked{

    Evaluation evaluation;
    private List<String> featureList = new ArrayList<>();

    public RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapterFeatures rcAdapter;
    RecyclerView rView;

    FloatingActionButton fab;
    private RelativeLayout fundoDinamic;

    private FirebaseAuth mAuth;
    FirebaseUser users;

    private IEvaluationDAO evaluationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation);

        if(getIntent() != null) {
            evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
            String pp = "";
        } else  {
            Alerts.toast(this, "Tente novamente");
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        evaluationDAO = new EvaluationDAO(evaluation.getId());
        evaluationDAO.findTotalEvaluation(evaluation, this::totalEvaluations);

        fundoDinamic = (RelativeLayout) findViewById(R.id.fundo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.DELIVERABLE.getName());
        setSupportActionBar(toolbar);
        updateUI(evaluation);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(users != null) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        AnimationsUtility.showCircularAnimationAvaliar(DetailEvaluationActivity.this, fundoDinamic, R.id.conteudo);
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DetailEvaluationActivity.this, AvaliandoActivity.class);
                            intent.putExtra("evaluation", evaluation);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                } else {
                    Alerts.toast(DetailEvaluationActivity.this, "Não logado.");
                }
            }
        });

        // List de Features
        this.featureList = evaluation.getFeatures();
        this.featureList.add(0, "Média Geral");

        // Recyleview
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setHasFixedSize(true);

        rcAdapter = new RecyclerViewAdapterFeatures(this, featureList);
        rView.setItemAnimator(new SlideInUpAnimator());
        rView.setAdapter(rcAdapter);
        rcAdapter.setOnClick(this);

        Grafico grafico = new Grafico(this);
        grafico.visaoGeral(evaluation, "Média Geral");

    }

    private void totalEvaluations(Object o) {
        TextView txtTotal = (TextView) findViewById(R.id.total);
        txtTotal.setText("" + o);
    }

    public void updateUI(Evaluation evaluation) {
        TextView txtTotal = (TextView) findViewById(R.id.total);
        txtTotal.setText(EvaluationUtil.getTotoalDeAvaliacoes(evaluation));

    }


    @Override
    public void onItemClickFeature(int position) {

        Grafico grafico = new Grafico(this);

        if (position == 0) {
            grafico.visaoGeral(evaluation, featureList.get(position));
        } else {
            grafico.featureName(evaluation, featureList.get(position));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AnimationsUtility.showCircularAnimationAvaliar(DetailEvaluationActivity.this, fundoDinamic, R.id.conteudo);
        }
    }
}
