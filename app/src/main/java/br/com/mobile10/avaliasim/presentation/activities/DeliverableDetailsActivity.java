package br.com.mobile10.avaliasim.presentation.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.RatingDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IRatingDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Rating;
import br.com.mobile10.avaliasim.presentation.dialogs.NewRatingDialog;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeliverableDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private IUserDAO userDAO;
    private IRatingDAO ratingDAO;
    private CoordinatorLayout coordinatorLayout;
    private Deliverable deliverable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverable_details_activity);

        deliverable = (Deliverable) getIntent().getSerializableExtra("deliverable");
        userDAO = new UserDAO();
        ratingDAO = new RatingDAO();
        ratingDAO.findAllByDeliverableId(deliverable.getId(), this::onRatingsLoaded);
    }

    private void onRatingsLoaded(Object result) {
        initializeViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(deliverable.getName());
        List<Rating> ratings = (List<Rating>) result;

        String text = "";
        if (ratings.size() == 0)
            text = "Nenhuma avaliação";
        else if (ratings.size() == 1)
            text = ratings.size() + " avaliação";
        else
            text = ratings.size() + " avaliações";

        ((TextView) findViewById(R.id.deliverable_sub_title)).setText(text);

        //Como definir se uma avaliação foi boa ou ruim? Quantidade de bons e ruins nas features avaliadas?
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
        findViewById(R.id.new_rating_btn).setOnClickListener(this::onNewRatingBtnClick);
    }

    private void onNewRatingBtnClick(View view) {
        RelativeLayout dynamicBackground = findViewById(R.id.fundo);
        if (userDAO.getLoggedUser() != null) {
            NewRatingDialog newRatingDialog = new NewRatingDialog(this);
            newRatingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            newRatingDialog.show();
//            new NewRatingDialog(this).show();
//            AnimationsUtility.showCircularAnimationAvaliar(DeliverableDetailsActivity.this, dynamicBackground, R.id.conteudo);
//            moverButtonParaDireita();

//            Intent intent = new Intent(DeliverableDetailsActivity.this, NewRatingActivity.class);
//                    intent.putExtra("avaliacao", avaliacao);
//            startActivity(intent);
        } else {
            finish();
            InterfaceUtils.showToast(this, "Faça login para criar uma avaliação");
        }
    }


    public void moverButtonParaDireita() {
        TranslateAnimation anim = new TranslateAnimation(0f, 600f, 0f, 0f);
        anim.setDuration(1500);
//        fab.startAnimation(anim);
    }

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
