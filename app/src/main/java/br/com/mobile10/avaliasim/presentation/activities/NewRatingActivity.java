package br.com.mobile10.avaliasim.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;


public class NewRatingActivity extends AppCompatActivity {

    TextView txtTitle, txtType, txtFeature;
    int quantidadeDeFeatures = 0;
    private RelativeLayout fundoDinamic;
    private ImageView imgFundoAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_rating_activity);

        //TODO: desacoplar features para criar as animações de cada

//        getIntent().getSerializableExtra("avaliacao");

//        fundoDinamic = findViewById(R.id.fundo);
//        imgFundoAnimation = findViewById(R.id.image_animation);
//        txtTitle = findViewById(R.id.title);
//        txtTitle.setText(avaliacao.title);
//
//        txtType = findViewById(R.id.type);
//        txtType.setText(avaliacao.type);
//
//        txtFeature = findViewById(R.id.feature);
//        updateUI();
    }

//    public void updateUI() {
//        try {
//            txtFeature.setText(avaliacao.features.get(quantidadeDeFeatures));
//        } catch (IndexOutOfBoundsException e) {
//            // Vai ate o ultimo item do array
//            e.printStackTrace();
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    showToast("Avaliado com sucesso!");
//                    finish();
//                }
//            }, 1000);
//        }
//    }

//    public void avaliarComoRuim(View view) {
//        avaliarFeatures(avaliacao, users.getUid(), "negative", avaliacao.features.get(quantidadeDeFeatures));
//        quantidadeDeFeatures++;
//        DetalhesAvaliacao.keyBuscaPorId = true; // certeza que avaliou
//
//        imgFundoAnimation.setImageResource(R.drawable.ic_mood_bad_black_24dp);
//        AnimationsUtility.showCircularAnimationRuim(this, fundoDinamic, R.id.conteudo);
//        // Tempo de volta da animacao
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AnimationsUtility.showCircularAnimationRuim(NewRatingActivity.this, fundoDinamic, R.id.conteudo);
//                updateUI();
//            }
//        }, 1000);
//    }
//
//    public void avaliarComoBom(View view) {
//        avaliarFeatures(avaliacao, users.getUid(), "positive", avaliacao.features.get(quantidadeDeFeatures));
//        quantidadeDeFeatures++;
//        DetalhesAvaliacao.keyBuscaPorId = true; // certeza que avaliou
//
//        imgFundoAnimation.setImageResource(R.drawable.ic_mood_black_24dp);
//        AnimationsUtility.showCircularAnimationBom(this, fundoDinamic, R.id.conteudo);
//        // Tempo de volta da animacao
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AnimationsUtility.showCircularAnimationBom(NewRatingActivity.this, fundoDinamic, R.id.conteudo);
//                updateUI();
//            }
//        }, 1000);
//    }
}
