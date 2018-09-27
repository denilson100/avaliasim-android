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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.EvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.presentation.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.util.Alerts;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.Constants;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AvaliandoActivity extends AppCompatActivity {

    private Evaluation evaluation;

    TextView txtTitle, txtType, txtFeature;
    int quantidadeDeFeatures = 0;
    private RelativeLayout fundoDinamic;
    private ImageView imgFundoAnimation;

    private IEvaluationDAO evaluationDAO;

    private FirebaseAuth mAuth;
    FirebaseUser users;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("dataAtual");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliando);

        if(getIntent() != null) {
            evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");
            evaluation.getFeatures().remove(0); // remove media geral
        } else  {
            Alerts.toast(this, "Tente novamente");
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        evaluationDAO = new EvaluationDAO(Constants.DELIVERABLE.getId());

        fundoDinamic = (RelativeLayout) findViewById(R.id.fundo);
        imgFundoAnimation = (ImageView) findViewById(R.id.image_animation);
        txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(Constants.DELIVERABLE.getName());

        txtType = (TextView) findViewById(R.id.type);
        txtType.setText(Constants.DELIVERABLE.getType());

        txtFeature = (TextView) findViewById(R.id.feature);
        updateUI();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Constants.DATA_ATUAL = (Long) dataSnapshot.child("timeStamp").getValue();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void updateUI() {
        try {
            txtFeature.setText(evaluation.getFeatures().get(quantidadeDeFeatures));
            String kk = "";
        } catch (IndexOutOfBoundsException e) {
            // Vai ate o ultimo item do array
            e.printStackTrace();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Alerts.toast(AvaliandoActivity.this,"Avaliado com sucesso!");
                    finish();
                }
            }, 1000);
        }
    }

    private void avaliar(Evaluation evaluation, String mUserId, String featureAvaliada, String status) {
        evaluationDAO.avaliar(evaluation, mUserId, featureAvaliada, status, null);
    }

    public void avaliarComoRuim(View view) {
        String status = "negative";
        avaliar(evaluation, users.getUid(), evaluation.getFeatures().get(quantidadeDeFeatures), status);
        quantidadeDeFeatures++;

        imgFundoAnimation.setImageResource(R.drawable.ic_mood_bad_black_24dp);
        AnimationsUtility.showCircularAnimationRuim(this, fundoDinamic, R.id.conteudo);
        // Tempo de volta da animacao
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationsUtility.showCircularAnimationRuim(AvaliandoActivity.this, fundoDinamic, R.id.conteudo);
                updateUI();
            }
        }, 1000);
    }

    public void avaliarComoBom(View view) {
        String status = "positive";
        avaliar(evaluation, users.getUid(), evaluation.getFeatures().get(quantidadeDeFeatures), status);
        quantidadeDeFeatures++;

        imgFundoAnimation.setImageResource(R.drawable.ic_mood_black_24dp);
        AnimationsUtility.showCircularAnimationBom(this, fundoDinamic, R.id.conteudo);
        // Tempo de volta da animacao
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationsUtility.showCircularAnimationBom(AvaliandoActivity.this, fundoDinamic, R.id.conteudo);
                updateUI();
            }
        }, 1000);
    }
}
