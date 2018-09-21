package br.com.mobile10.avaliasim.presentation.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Deliverable;
import im.dacer.androidcharts.LineView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeliverableDetailsActivity extends AppCompatActivity
//        extends BaseActivity implements RecyclerViewAdapterDetAvaliacoes.OnItemClicked,
//        RecyclerViewAdapterFeatures.OnItemClicked, DatePickerFragmentDialog.OnDateSetListener,
//        DateRangePickerFragment.OnDateRangeSelectedListener, DatePickerFragment.OnDateSelectedListener
{
    private List<String> featureList = new ArrayList<>();

    public static boolean keyBuscaPorId;
    private RelativeLayout fundoDinamic;
    private FloatingActionButton fab;
    private FloatingActionButton fabAllDate;
    private FloatingActionButton fabRangeDate;
    private FloatingActionButton fabUnicDate;
    private TextView txtData;
    private TextView txtTotal;
    private Toolbar toolbar;
    private TextView txtType;
    private LineView lineView;
    private Button lineButton;
    //    private RecyclerViewAdapterFeatures rcAdapter;
    private RecyclerView recyclerView;

    private int key; // chave para buscar grafico. 0 = geral, 1 = busca data unica, 2 = busca com range de datas
    private final int geral = 0;
    private final int unicDate = 1;
    private final int rangeDate = 2;
    int randomint = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverable_details_activity);

        initializeViews();

        Deliverable deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        toolbar.setTitle(deliverable.getName());
        setSupportActionBar(toolbar);
//        txtType.setText(avaliacao.type);
//

//        txtTotal.setText(AvalicaoUtil.getTotoalDeAvaliacoes(avaliacao));

//        this.featureList = AvalicaoUtil.getListFeature(avaliacao);
//        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        rView.setHasFixedSize(true);
//
//        rcAdapter = new RecyclerViewAdapterFeatures(this, featureList);
//        rView.setItemAnimator(new SlideInUpAnimator());
//        rView.setAdapter(rcAdapter);
//        rcAdapter.setOnClick(this);
//
//        Graphic graphic = new Graphic(this);
//        graphic.visaoGeral(avaliacao, "Média Geral");
//

//        initLineView(lineView);

//        lineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                randomSet(lineView);
//            }
//        });
//        randomSet(lineView);
    }

    private void initializeViews() {
        lineView = findViewById(R.id.line_view);
        lineButton = findViewById(R.id.line_button);
//        txtData = findViewById(R.id.data);
        fundoDinamic = findViewById(R.id.fundo);
        toolbar = findViewById(R.id.toolbar);
        txtType = findViewById(R.id.type);
        txtTotal = findViewById(R.id.total);
//        fabAllDate = findViewById(R.id.fab_all_date);
//        fabRangeDate = findViewById(R.id.fab_range_date);
//        fabUnicDate = findViewById(R.id.fab_unic_date);
//        fab = findViewById(R.id.fab);
//        rView = findViewById(R.id.recycler_view);


        fab.setOnClickListener(view -> {
//            Se o usuário está logado...
//            if (users != null) {
//                AnimationsUtility.showCircularAnimationAvaliar(RatingDetails.this, fundoDinamic, R.id.conteudo);
//                moverButtonParaDireita();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(DeliverableDetailsActivity.this, Feature1Activity.class);
//                        intent.putExtra("avaliacao", avaliacao);
//                        startActivity(intent);
//                    }
//                }, 1000);
//            } else {
//                TODO:
//                passar para o fragment de login quando o usuário não estiver autenticado
//                Intent intent = new Intent(DetalhesAvaliacao.this, EmailPasswordActivity.class);
//                startActivity(intent);
//                finish();
//            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (keyBuscaPorId) {
//            executeAsyncTaskGetAvaliacaoPorId();
//            keyBuscaPorId = false;
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        // Quando sair da tela, retorna animação ao normal
//        AnimationsUtility.showCircularAnimationAvaliar(this, fundoDinamic, R.id.conteudo);
//    }

//    private void executeAsyncTaskGetAvaliacaoPorId() {
////        new LoadingAvaliacaoPorID(this, avaliacao.idAvaliacao).execute();
//    }

//    public void setAvaliacoesTotal(String result) {
//        if (result != null)
//            txtTotal.setText(result);
//    }

//    public void moverButtonParaDireita() {
//        TranslateAnimation anim = new TranslateAnimation(0f, 600f, 0f, 0f);
//        anim.setDuration(1500);
//
//        fab.startAnimation(anim);
//    }

//    @Override
//    public void onItemClick(int position) {
//
//    }

//    public void setAvaliacaoPorId(Avaliacao2 avaliacao) {
//        if (avaliacao != null)
//            updateUI(avaliacao);
//    }

//    @Override
//    public void onItemClickFeature(int position) {
//        Log.d("TAG", "Click: " + featureList.get(position));
//
//        Graphic graphic = new Graphic(this);
//
//        switch (key) {
//            case geral:
//                if (position == 0) {
//                    graphic.visaoGeral(avaliacao, featureList.get(position));
//                } else {
//                    graphic.featureName(avaliacao, featureList.get(position));
//                }
//                break;
//            case unicDate:
//                if (position == 0) {
//                    graphic.visaoGeralUnicDate(avaliacao, featureList.get(position));
//                } else {
//                    graphic.featureNameUnicDate(avaliacao, featureList.get(position));
//                }
//                break;
//            case rangeDate:
//                if (position == 0) {
//                    graphic.visaoGeralRangeDate(avaliacao, featureList.get(position));
//                } else {
//                    graphic.featureNameRangeDate(avaliacao, featureList.get(position));
//                }
//                break;
//            default:
//                // erro
//        }
//
//    }

//    @Override
//    public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
//        Log.d("TAG", "Ano: " + year + " Mes: " + monthOfYear + " Dia: " + dayOfMonth);
//    }

//    @Override
//    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
//        Log.d("range : ", "from: " + startDay + "-" + (startMonth + 1) + "-" + startYear + " to : " + endDay + "-" + (endMonth + 1) + "-" + endYear);
//        txtData.setText("Data: " + startDay + "/" + (startMonth + 1) + "/" + startYear + " a " + endDay + "/" + (endMonth + 1) + "/" + endYear);
////        Constants.DATE1 = Format.convertStringInDate(startDay + "-" + (startMonth + 1) + "-" + startYear);
////        Constants.DATE2 = Format.convertStringInDate(endDay + "-" + (endMonth + 1) + "-" + endYear);
//        key = rangeDate;
//
//    }

//    public void clickIconAllDate(View view) {
//        updateColorFab("all");
//    }

//    public void clickIconDateRange(View view) {
//        DateRangePickerFragment dateRangePickerFragment = DateRangePickerFragment.newInstance(RatingDetails.this, false);
//        dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker");
//
//        updateColorFab("range");
//    }

    //    public void clickIconUnicDate(View view) {
//        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(RatingDetails.this, false);
//        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
//
//        updateColorFab("unic");
//    }

//    @Override
//    public void onDateSelected(int day, int month, int year) {
//        Log.d("range", "" + day + "-" + (month + 1) + "-" + year);
//        txtData.setText("Data: " + day + "/" + (month + 1) + "/" + year);
//
//        String dateInString = day + "-" + (month + 1) + "-" + year;
////        Constants.DATE_UNIC = Format.convertStringInDate(dateInString);
//        key = unicDate;
//
//    }

//    public void updateColorFab(String fab) {
//        switch (fab) {
//            case "all":
//                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
//                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                break;
//            case "range":
//                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
//                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                break;
//            case "unic":
//                fabAllDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                fabRangeDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cinza)));
//                fabUnicDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
//                break;
//            default:
//                // erro
//        }
//    }

//    private void initLineView(LineView lineView) {
//        ArrayList<String> test = new ArrayList<String>();
//        for (int i = 0; i < randomint; i++) {
//            test.add(String.valueOf(i + 1));
//        }
//        lineView.setBottomTextList(test);
//        lineView.setColorArray(new int[]{
//                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
//                Color.parseColor("#2196F3"), Color.parseColor("#009688")
//        });
//        lineView.setDrawDotLine(true);
//        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
//    }

//    private void randomSet(LineView lineView) {
//        ArrayList<Integer> dataList = new ArrayList<>();
//        float random = (float) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++)
//            dataList.add((int) (Math.random() * random));
//
//        ArrayList<Integer> dataList2 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++)
//            dataList2.add((int) (Math.random() * random));
//
//        ArrayList<Integer> dataList3 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++)
//            dataList3.add((int) (Math.random() * random));
//
//        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
//        dataLists.add(dataList);
//        dataLists.add(dataList2);
//        dataLists.add(dataList3);
//
//        lineView.setDataList(dataLists);
//
//    }

}
