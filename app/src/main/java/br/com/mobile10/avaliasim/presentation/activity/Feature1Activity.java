package br.com.mobile10.avaliasim.presentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.FeatureDaoImplementacao;
import br.com.mobile10.avaliasim.data.interfaces.FeatureDao;
import br.com.mobile10.avaliasim.model.Avaliacao2;
import br.com.mobile10.avaliasim.util.AnimationsUtility;
import br.com.mobile10.avaliasim.util.BaseActivity;

/**
 * Created by denmont on 16/04/2018.
 */
public class Feature1Activity extends BaseActivity implements FeatureDao {

    Avaliacao2 avaliacao;
    private FirebaseAuth mAuth;
    FirebaseUser users;

    TextView txtTitle, txtType, txtFeature;
    int quantidadeDeFeatures = 0;
    private RelativeLayout fundoDinamic;
    private ImageView imgFundoAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature1);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if(getIntent() != null) {
            avaliacao = (Avaliacao2) getIntent().getSerializableExtra("avaliacao");
        } else  {
//            showToast("Tente novamente");
            finish();
        }

        fundoDinamic = findViewById(R.id.fundo);
        imgFundoAnimation = findViewById(R.id.image_animation);
        txtTitle = findViewById(R.id.title);
        txtTitle.setText(avaliacao.title);

        txtType = findViewById(R.id.type);
        txtType.setText(avaliacao.type);

        txtFeature = findViewById(R.id.feature);
        updateUI();
    }

    public void updateUI() {
        try {
            txtFeature.setText(avaliacao.features.get(quantidadeDeFeatures));
        } catch (IndexOutOfBoundsException e) {
            // Vai ate o ultimo item do array
            e.printStackTrace();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    showToast("Avaliado com sucesso!");
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    public void avaliarFeatures(Avaliacao2 avaliacao, String userId, String status, String featureAvaliada) {
        FeatureDaoImplementacao dao = new FeatureDaoImplementacao();
//        dao.avaliarFeatures(avaliacao, userId, status, featureAvaliada);
    }

    public void avaliarComoRuim(View view) {
        avaliarFeatures(avaliacao, users.getUid(), "negative", avaliacao.features.get(quantidadeDeFeatures));
        quantidadeDeFeatures++;
        DetalhesAvaliacao.keyBuscaPorId = true; // certeza que avaliou

        imgFundoAnimation.setImageResource(R.drawable.ic_mood_bad_black_24dp);
        AnimationsUtility.showCircularAnimationRuim(this, fundoDinamic, R.id.conteudo);
        // Tempo de volta da animacao
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationsUtility.showCircularAnimationRuim(Feature1Activity.this, fundoDinamic, R.id.conteudo);
                updateUI();
            }
        }, 1000);
    }

    public void avaliarComoBom(View view) {
        avaliarFeatures(avaliacao, users.getUid(), "positive", avaliacao.features.get(quantidadeDeFeatures));
        quantidadeDeFeatures++;
        DetalhesAvaliacao.keyBuscaPorId = true; // certeza que avaliou

        imgFundoAnimation.setImageResource(R.drawable.ic_mood_black_24dp);
        AnimationsUtility.showCircularAnimationBom(this, fundoDinamic, R.id.conteudo);
        // Tempo de volta da animacao
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationsUtility.showCircularAnimationBom(Feature1Activity.this, fundoDinamic, R.id.conteudo);
                updateUI();
            }
        }, 1000);
    }
}
