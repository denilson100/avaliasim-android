package br.com.mobile10.avaliasim.presentation.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.util.Alerts;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AvaliandoActivity extends AppCompatActivity {

    private Evaluation evaluation;
    private TextView txtTitle;
    private TextView txtType;
    private TextView txtFeature;
    private int quantidadeDeFeatures = 0;
    private RelativeLayout fundoDinamic;
    private ImageView imgFundoAnimation;
    private IEvaluationDAO evaluationDAO;

    private IUserDAO userDAO;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("dataAtual");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliando);
        initializeViews();
        evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
        evaluationDAO = new EvaluationDAO(Constants.DELIVERABLE.getId());
        userDAO = new UserDAO();

        evaluation.getFeatures().remove(0);
        txtTitle.setText(Constants.DELIVERABLE.getName());
        txtType.setText(Constants.DELIVERABLE.getType());
        updateUI();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Constants.DATA_ATUAL = (Long) dataSnapshot.child("timeStamp").getValue();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initializeViews() {
        fundoDinamic = findViewById(R.id.fundo);
        imgFundoAnimation = findViewById(R.id.image_animation);
        txtTitle = findViewById(R.id.title);
        txtType = findViewById(R.id.type);
        txtFeature = findViewById(R.id.feature);
        findViewById(R.id.good_btn).setOnClickListener(this::avaliarComoBom);
        findViewById(R.id.bad_btn).setOnClickListener(this::avaliarComoRuim);
    }

    public void updateUI() {
        try {
            txtFeature.setText(evaluation.getFeatures().get(quantidadeDeFeatures));
        } catch (IndexOutOfBoundsException e) {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Alerts.toast(AvaliandoActivity.this, "Avaliado com sucesso!");
                finish();
            }, 1000);
        }
    }

    public void avaliarComoRuim(View view) {
        evaluationDAO.avaliar(evaluation, userDAO.getLoggedUser().getUid(), evaluation.getFeatures().get(quantidadeDeFeatures), "negative", null);
        quantidadeDeFeatures++;
        imgFundoAnimation.setImageResource(R.drawable.ic_mood_bad_black_24dp);
        AnimationsUtility.showCircularAnimationRuim(this, fundoDinamic, R.id.conteudo);
        // Tempo de volta da animacao
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            AnimationsUtility.showCircularAnimationRuim(AvaliandoActivity.this, fundoDinamic, R.id.conteudo);
            updateUI();
        }, 1000);
    }

    public void avaliarComoBom(View view) {
        evaluationDAO.avaliar(evaluation, userDAO.getLoggedUser().getUid(), evaluation.getFeatures().get(quantidadeDeFeatures), "positive", null);
        quantidadeDeFeatures++;
        imgFundoAnimation.setImageResource(R.drawable.ic_mood_black_24dp);
        AnimationsUtility.showCircularAnimationBom(this, fundoDinamic, R.id.conteudo);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            AnimationsUtility.showCircularAnimationBom(AvaliandoActivity.this, fundoDinamic, R.id.conteudo);
            updateUI();
        }, 1000);
    }
}
