package br.com.mobile10.avaliasim.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapterDetAvaliacoes;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapterFeatures;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacaoPorID;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoesGetTotal;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.fragments.DatePickerFragment;
import br.com.mobile10.avaliasim.fragments.DateRangePickerFragment;
import br.com.mobile10.avaliasim.libBarGraph.HorizontalBar;
import br.com.mobile10.avaliasim.libBarGraph.model.BarItem;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.AvalicaoUtil;
import br.com.mobile10.avaliasim.util.BaseActivity;
import br.com.mobile10.avaliasim.util.Grafico;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DetalhesAvaliacao extends BaseActivity implements RecyclerViewAdapterDetAvaliacoes.OnItemClicked,
        RecyclerViewAdapterFeatures.OnItemClicked, DatePickerFragmentDialog.OnDateSetListener,
        DateRangePickerFragment.OnDateRangeSelectedListener, DatePickerFragment.OnDateSelectedListener {

    Avaliacao2 avaliacao;
    private List<String> featureList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseUser users;

    TextView txtTitle, txtType;
    HorizontalBar horizontal;
    public static boolean keyBuscaPorId;
    private RelativeLayout fundoDinamic;
    ObjectAnimator objectanimator;
    FloatingActionButton fab;
    TextView txtData;

    public RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapterFeatures rcAdapter;
    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_avaliacao2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if(getIntent() != null) {
            avaliacao = (Avaliacao2) getIntent().getSerializableExtra("avaliacao");
        } else  {
            showToast("Tente novamente");
            finish();
        }

        txtData = (TextView) findViewById(R.id.data);
        fundoDinamic = (RelativeLayout) findViewById(R.id.fundo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(avaliacao.title);
        setSupportActionBar(toolbar);
        updateUI(avaliacao);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(users != null) {

                    AnimationsUtility.showCircularAnimationAvaliar(DetalhesAvaliacao.this, fundoDinamic, R.id.conteudo);

                    moverButtonParaDireita();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DetalhesAvaliacao.this, Feature1Activity.class);
                            intent.putExtra("avaliacao", avaliacao);
                            startActivity(intent);
                        }
                    }, 1000);

                } else {
                    Intent intent = new Intent(DetalhesAvaliacao.this, EmailPasswordActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // List de Features
        this.featureList = AvalicaoUtil.getListFeature(avaliacao);

        // Recyleview
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setHasFixedSize(true);

        rcAdapter = new RecyclerViewAdapterFeatures(this, featureList);
        rView.setItemAnimator(new SlideInUpAnimator());
        rView.setAdapter(rcAdapter);
        rcAdapter.setOnClick(this);

//        updateGrafico(titleGrafico);
        Grafico grafico = new Grafico(this);
//        grafico.featureName(avaliacao, AvalicaoUtil.getListFeature(avaliacao).get(0));
        grafico.visaoGeral(avaliacao, "Visão Geral");

    }


    public void updateUI(Avaliacao2 avaliacao) {
//        TextView txtTitle = (TextView) findViewById(R.id.title);
//        txtTitle.setText(avaliacao.title);

        TextView txtType = (TextView) findViewById(R.id.type);
        txtType.setText(avaliacao.type);

        TextView txtTotal = (TextView) findViewById(R.id.total);
        txtTotal.setText(AvalicaoUtil.getTotoalDeAvaliacoes(avaliacao));

//        horizontal = findViewById(R.id.horizontal);
//        horizontal.init(this).hasAnimation(true).addAll(itens(avaliacao)).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(keyBuscaPorId) {
            executeAsyncTaskGetAvaliacaoPorId();
            keyBuscaPorId = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Quando sair da tela, retorna animação ao normal
        AnimationsUtility.showCircularAnimationAvaliar(this, fundoDinamic, R.id.conteudo);
    }

    private void executeAsyncTaskGetTotal() {
        new LoadingAvaliacoesGetTotal(this, avaliacao).execute();
    }

    private void executeAsyncTaskGetAvaliacaoPorId() {
        new LoadingAvaliacaoPorID(this, avaliacao.idAvaliacao).execute();
    }

    public void setAvaliacoesTotal(String result) {
        TextView txtTotal = (TextView) findViewById(R.id.total);
        if(result != null) {
            txtTotal.setText(result);
//            atualizarLista(avaliacao.listaAvaliacoes);
        }
    }

    public void moverButtonParaDireita() {
        TranslateAnimation anim = new TranslateAnimation(0f, 600f, 0f, 0f);
        anim.setDuration(1500);

        fab.startAnimation(anim);
    }

    @Override
    public void onItemClick(int position) {

    }

    private List<BarItem> itens(Avaliacao2 avaliacao) {
        horizontal.removeAll();
        List<BarItem> items = new ArrayList<>();

        for (Feature feature : avaliacao.listaAvaliacoes) {
            int positive = 0;
            int negative = 0;
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }

            items.add(new BarItem(feature.name, (double) positive, (double) negative,
                    ContextCompat.getColor(this, R.color.verde),
                    ContextCompat.getColor(this, R.color.vermelho),
                    Color.WHITE,
                    Color.WHITE));
        }

        return items;
    }

    public void setAvaliacaoPorId(Avaliacao2 avaliacao) {
        if(avaliacao != null)
            updateUI(avaliacao);
    }

    public void timer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeAsyncTaskGetAvaliacaoPorId();
                hideLoadingIndictor();
            }
        }, 3000);
    }

    @Override
    public void onItemClickFeature(int position) {
        Log.d("TAG", "Click: " + featureList.get(position));
        Grafico grafico = new Grafico(this);

        if (position == 0) {
            grafico.visaoGeral(avaliacao, featureList.get(position));
        } else {
            grafico.featureName(avaliacao, featureList.get(position));
        }
    }

    public void startCalendar() {
        DateRangePickerFragment dateRangePickerFragment= DateRangePickerFragment.newInstance(DetalhesAvaliacao.this,false);
        dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("TAG", "Ano: " + year + " Mes: " + monthOfYear + " Dia: " + dayOfMonth);
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        Log.d("range : ","from: "+startDay+"-"+startMonth+"-"+startYear+" to : "+endDay+"-"+endMonth+"-"+endYear );
        txtData.setText("Data: " + startDay + "/" + startMonth + "/" + startYear + " a " + endDay + "/" + endMonth + "/" + endYear);

    }

    public void clickIconDateRange(View view) {
        startCalendar();
    }

    public void clickIconDateToDay(View view) {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(DetalhesAvaliacao.this,false);
        datePickerFragment.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void onDateSelected(int day, int month, int year) {
        Log.d("TAG", "Ano: " + year + " Mes: " + month + " Dia: " + day);
        txtData.setText("Data: " + day + "/" + month + "/" + year);
    }
}
