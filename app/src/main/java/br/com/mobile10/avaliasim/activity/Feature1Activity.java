package br.com.mobile10.avaliasim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.dao.AvaliacaoDaoImplementacao;
import br.com.mobile10.avaliasim.dao.FeatureDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.AvaliacaoDao;
import br.com.mobile10.avaliasim.interfaces.FeatureDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Produto;
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
            showToast("Tente novamente");
            finish();
        }

        txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(avaliacao.title);

        txtType = (TextView) findViewById(R.id.type);
        txtType.setText(avaliacao.type);

        txtFeature = (TextView) findViewById(R.id.feature);
        updateUI();

//        avaliarFeature1(avaliacao, users.getUid(), "positive");
    }

    public void updateUI() {
        try {
            txtFeature.setText(avaliacao.features.get(quantidadeDeFeatures));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            showToast("Avaliado com sucesso!");
            finish();
        }
    }


    @Override
    public void avaliarFeatures(Avaliacao2 avaliacao, String userId, String status, String featureAvaliada) {
        FeatureDaoImplementacao dao = new FeatureDaoImplementacao();
        dao.avaliarFeatures(avaliacao, userId, status, featureAvaliada);
    }

    public void avaliarComoRuim(View view) {
        avaliarFeatures(avaliacao, users.getUid(), "negative", avaliacao.features.get(quantidadeDeFeatures));
        quantidadeDeFeatures++;
        updateUI();
    }

    public void avaliarComoBom(View view) {
        avaliarFeatures(avaliacao, users.getUid(), "positive", avaliacao.features.get(quantidadeDeFeatures));
        quantidadeDeFeatures++;
        updateUI();
    }
}
