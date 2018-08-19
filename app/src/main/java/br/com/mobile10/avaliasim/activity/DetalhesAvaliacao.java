package br.com.mobile10.avaliasim.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
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
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.fragments.DatePickerFragment;
import br.com.mobile10.avaliasim.fragments.DateRangePickerFragment;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.AvalicaoUtil;
import br.com.mobile10.avaliasim.util.BaseActivity;
import br.com.mobile10.avaliasim.util.Constantes;
import br.com.mobile10.avaliasim.util.Format;
import br.com.mobile10.avaliasim.util.Grafico;
import im.dacer.androidcharts.LineView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DetalhesAvaliacao extends BaseActivity implements RecyclerViewAdapterDetAvaliacoes.OnItemClicked,
        RecyclerViewAdapterFeatures.OnItemClicked, DatePickerFragmentDialog.OnDateSetListener,
        DateRangePickerFragment.OnDateRangeSelectedListener, DatePickerFragment.OnDateSelectedListener {

    Avaliacao2 avaliacao;
    private List<String> featureList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseUser users;

    public static boolean keyBuscaPorId;
    private RelativeLayout fundoDinamic;
    FloatingActionButton fab, fabAllDate, fabRangeDate, fabUnicDate;
    TextView txtData;

    RecyclerViewAdapterFeatures rcAdapter;
    RecyclerView rView;

    private int key; // chave para buscar grafico. 0 = geral, 1 = busca data unica, 2 = busca com range de datas
    private final int geral = 0;
    private final int unicDate = 1;
    private final int rangeDate = 2;

    int randomint = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_avaliacao2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if (getIntent() != null) {
            avaliacao = (Avaliacao2) getIntent().getSerializableExtra("avaliacao");
        } else {
            showToast("Tente novamente");
            finish();
        }

        txtData = findViewById(R.id.data);
        fundoDinamic = findViewById(R.id.fundo);
        fundoDinamic = findViewById(R.id.fundo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(avaliacao.title);
        setSupportActionBar(toolbar);
        updateUI(avaliacao);

        fabAllDate = findViewById(R.id.fab_all_date);
        fabRangeDate = findViewById(R.id.fab_range_date);
        fabUnicDate = findViewById(R.id.fab_unic_date);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (users != null) {

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
        rView = findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setHasFixedSize(true);

        rcAdapter = new RecyclerViewAdapterFeatures(this, featureList);
        rView.setItemAnimator(new SlideInUpAnimator());
        rView.setAdapter(rcAdapter);
        rcAdapter.setOnClick(this);

        Grafico grafico = new Grafico(this);
        grafico.visaoGeral(avaliacao, "Média Geral");

        final LineView lineView = (LineView) findViewById(R.id.line_view);
        initLineView(lineView);
        Button lineButton = findViewById(R.id.line_button);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(lineView);
            }
        });
        randomSet(lineView);

    }


    public void updateUI(Avaliacao2 avaliacao) {
        TextView txtType = findViewById(R.id.type);
        txtType.setText(avaliacao.type);

        TextView txtTotal = findViewById(R.id.total);
        txtTotal.setText(AvalicaoUtil.getTotoalDeAvaliacoes(avaliacao));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (keyBuscaPorId) {
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

    private void executeAsyncTaskGetAvaliacaoPorId() {
        new LoadingAvaliacaoPorID(this, avaliacao.idAvaliacao).execute();
    }

    public void setAvaliacoesTotal(String result) {
        TextView txtTotal = findViewById(R.id.total);
        if (result != null)
            txtTotal.setText(result);
    }

    public void moverButtonParaDireita() {
        TranslateAnimation anim = new TranslateAnimation(0f, 600f, 0f, 0f);
        anim.setDuration(1500);

        fab.startAnimation(anim);
    }

    @Override
    public void onItemClick(int position) {

    }

    public void setAvaliacaoPorId(Avaliacao2 avaliacao) {
        if (avaliacao != null)
            updateUI(avaliacao);
    }

    @Override
    public void onItemClickFeature(int position) {
        Log.d("TAG", "Click: " + featureList.get(position));

        Grafico grafico = new Grafico(this);

        switch (key) {
            case geral:
                if (position == 0) {
                    grafico.visaoGeral(avaliacao, featureList.get(position));
                } else {
                    grafico.featureName(avaliacao, featureList.get(position));
                }
                break;
            case unicDate:
                if (position == 0) {
                    grafico.visaoGeralUnicDate(avaliacao, featureList.get(position));
                } else {
                    grafico.featureNameUnicDate(avaliacao, featureList.get(position));
                }
                break;
            case rangeDate:
                if (position == 0) {
                    grafico.visaoGeralRangeDate(avaliacao, featureList.get(position));
                } else {
                    grafico.featureNameRangeDate(avaliacao, featureList.get(position));
                }
                break;
            default:
                // erro
        }

    }

    @Override
    public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("TAG", "Ano: " + year + " Mes: " + monthOfYear + " Dia: " + dayOfMonth);
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        Log.d("range : ", "from: " + startDay + "-" + (startMonth + 1) + "-" + startYear + " to : " + endDay + "-" + (endMonth + 1) + "-" + endYear);
        txtData.setText("Data: " + startDay + "/" + (startMonth + 1) + "/" + startYear + " a " + endDay + "/" + (endMonth + 1) + "/" + endYear);
        Constantes.DATE1 = Format.convertStringInDate(startDay + "-" + (startMonth + 1) + "-" + startYear);
        Constantes.DATE2 = Format.convertStringInDate(endDay + "-" + (endMonth + 1) + "-" + endYear);
        key = rangeDate;

    }

    public void clickIconAllDate(View view) {
        updateColorFab("all");
    }

    public void clickIconDateRange(View view) {
        DateRangePickerFragment dateRangePickerFragment = DateRangePickerFragment.newInstance(DetalhesAvaliacao.this, false);
        dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker");

        updateColorFab("range");
    }

    public void clickIconUnicDate(View view) {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(DetalhesAvaliacao.this, false);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");

        updateColorFab("unic");
    }

    @Override
    public void onDateSelected(int day, int month, int year) {
        Log.d("range", "" + day + "-" + (month + 1) + "-" + year);
        txtData.setText("Data: " + day + "/" + (month + 1) + "/" + year);

        String dateInString = day + "-" + (month + 1) + "-" + year;
        Constantes.DATE_UNIC = Format.convertStringInDate(dateInString);
        key = unicDate;

    }

    public void updateColorFab(String fab) {
        switch (fab) {
            case "all":
                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                break;
            case "range":
                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                break;
            case "unic":
                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
                break;
            default:
                // erro
        }
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[]{
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView) {
        ArrayList<Integer> dataList = new ArrayList<>();
        float random = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++)
            dataList.add((int) (Math.random() * random));

        ArrayList<Integer> dataList2 = new ArrayList<>();
        random = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++)
            dataList2.add((int) (Math.random() * random));

        ArrayList<Integer> dataList3 = new ArrayList<>();
        random = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++)
            dataList3.add((int) (Math.random() * random));

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);
        dataLists.add(dataList2);
        dataLists.add(dataList3);

        lineView.setDataList(dataLists);

    }

}
