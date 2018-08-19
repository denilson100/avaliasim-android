package br.com.mobile10.avaliasim.activity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.dao.AvaliacaoDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.AvaliacaoDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.BaseActivity;

/**
 * Created by denmont on 16/04/2018.
 */

public class AvaliacaActivity extends BaseActivity implements AvaliacaoDao {

    Avaliacao2 avaliacao;
    private FirebaseAuth mAuth;
    FirebaseUser users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if(getIntent() != null) {
            avaliacao = (Avaliacao2) getIntent().getSerializableExtra("avaliacao");
        } else  {
            showToast("Tente novamente");
        }
    }

    @Override
    public void newAvaliacao(Avaliacao2 avaliacao, String userId) {
        AvaliacaoDaoImplementacao dao = new AvaliacaoDaoImplementacao();
        dao.newAvaliacao(avaliacao, userId);
    }

}
