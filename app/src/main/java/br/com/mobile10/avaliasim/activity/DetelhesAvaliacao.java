package br.com.mobile10.avaliasim.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.comparsechart.CompareIndicator;
import com.google.firebase.auth.EmailAuthCredential;
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
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.AvalicaoUtil;
import br.com.mobile10.avaliasim.util.BaseActivity;

/**
 * Created by denmont on 16/04/2018.
 */

public class DetelhesAvaliacao extends BaseActivity implements RecyclerViewAdapterDetAvaliacoes.OnItemClicked {

    Avaliacao2 avaliacao;
    private List<Feature> featureList = new ArrayList<Feature>();
    private FirebaseAuth mAuth;
    FirebaseUser users;

    TextView txtTitle, txtType;
    HorizontalBar horizontal;
    public static boolean keyBuscaPorId;

    private RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapterDetAvaliacoes rcAdapter;
    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if(getIntent() != null) {
            avaliacao = (Avaliacao2) getIntent().getSerializableExtra("avaliacao");
        } else  {
            showToast("Tente novamente");
            finish();
        }

        updateUI(avaliacao);


//        lLayout = new LinearLayoutManager(this);
//        rView = (RecyclerView) findViewById(R.id.recycler_view);
//        rView.setHasFixedSize(true);
//        rView.setLayoutManager(lLayout);
//        atualizarLista(avaliacao.listaAvaliacoes);

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
        TextView txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(avaliacao.title);

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
//        executeAsyncTaskGetTotal();
        if(keyBuscaPorId) {
            executeAsyncTaskGetAvaliacaoPorId();
//            timer();
//            showLoadingIndicator();
            keyBuscaPorId = false;
        }
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

    public void avaliar(View view) {
        if(users != null) {
            Intent intent = new Intent(this, Feature1Activity.class);
            intent.putExtra("avaliacao", avaliacao);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            finish();
        }
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
