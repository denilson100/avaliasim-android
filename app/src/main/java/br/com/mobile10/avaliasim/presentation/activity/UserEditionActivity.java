package br.com.mobile10.avaliasim.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.User;

public class UserEditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edition_activity);
        setTitle("Editar informações");
        User loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
    }

}
