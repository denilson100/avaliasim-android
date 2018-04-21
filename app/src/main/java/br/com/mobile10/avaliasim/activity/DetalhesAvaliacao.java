package br.com.mobile10.avaliasim.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapterDetAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacaoPorID;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoesGetTotal;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.libBarGraph.HorizontalBar;
import br.com.mobile10.avaliasim.libBarGraph.model.BarItem;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.AvalicaoUtil;
import br.com.mobile10.avaliasim.util.BaseActivity;

public class DetalhesAvaliacao extends BaseActivity implements RecyclerViewAdapterDetAvaliacoes.OnItemClicked {

    Avaliacao2 avaliacao;
    private List<Feature> featureList = new ArrayList<Feature>();
    private FirebaseAuth mAuth;
    FirebaseUser users;

    TextView txtTitle, txtType;
    HorizontalBar horizontal;
    public static boolean keyBuscaPorId;
    private RelativeLayout fundoDinamic;
    ObjectAnimator objectanimator;
    FloatingActionButton fab;

    private RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapterDetAvaliacoes rcAdapter;
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

        fundoDinamic = (RelativeLayout) findViewById(R.id.fundo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(avaliacao.title);
        setSupportActionBar(toolbar);
        updateUI(avaliacao);

//        lLayout = new LinearLayoutManager(this);
//        rView = (RecyclerView) findViewById(R.id.recycler_view);
//        rView.setHasFixedSize(true);
//        rView.setLayoutManager(lLayout);
//        atualizarLista(avaliacao.listaAvaliacoes);


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

//        objectanimator = ObjectAnimator.ofFloat(fab,"x",1200);

    }

    //    public void atualizarLista(List<Feature> itemList) {
//        try {
//            rcAdapter = new RecyclerViewAdapterDetAvaliacoes(this, itemList);
//            rView.setAdapter(rcAdapter);
//            rcAdapter.setOnClick(this);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            showToast("Erro");
//        }
//    }

    public void updateUI(Avaliacao2 avaliacao) {
//        TextView txtTitle = (TextView) findViewById(R.id.title);
//        txtTitle.setText(avaliacao.title);

        TextView txtType = (TextView) findViewById(R.id.type);
        txtType.setText(avaliacao.type);

        TextView txtTotal = (TextView) findViewById(R.id.total);
        txtTotal.setText(AvalicaoUtil.getTotoalDeAvaliacoes(avaliacao));

        horizontal = findViewById(R.id.horizontal);
        horizontal.init(this).hasAnimation(true).addAll(itens(avaliacao)).build();
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

//    public void avaliar(View view) {
//        if(users != null) {
//            Intent intent = new Intent(this, Feature1Activity.class);
//            intent.putExtra("avaliacao", avaliacao);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(this, EmailPasswordActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

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
}
