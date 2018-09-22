package br.com.mobile10.avaliasim.presentation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.CodeUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeliverableDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private IUserDAO userDAO;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverable_details_activity);

        Deliverable deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        userDAO = new UserDAO();

        initializeViews();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pineng PN-888");
        }

//        TODO: pegar quantidade total de avaliações do produto e setar no textview
        ((TextView) findViewById(R.id.deliverable_sub_title)).setText("X avaliações");
        toolbar.setTitle(deliverable.getName());

        PieChart pieChart = findViewById(R.id.pie_chart);
        List<ChartData> data = new ArrayList<>();
        data.add(new ChartData("Bom (35%)", 35, Color.WHITE, Color.parseColor("#00FF00")));
        data.add(new ChartData("Ruim (65%)", 65, Color.WHITE, Color.parseColor("#FF0000")));
        pieChart.setChartData(data);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        coordinatorLayout = findViewById(R.id.coordinator_container);

        findViewById(R.id.new_rating_btn).setOnClickListener(view -> {
            CodeUtils.showSnackbar(coordinatorLayout, "FAB!");
//            if (userDAO.getLoggedUser() != null) {
                //TODO: continuar animação
//                AnimationsUtility.showCircularAnimationAvaliar(DeliverableDetailsActivity.this, fundoDinamic, R.id.conteudo);
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
////                TODO: passar para o fragment de login quando o usuário não estiver autenticado
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
