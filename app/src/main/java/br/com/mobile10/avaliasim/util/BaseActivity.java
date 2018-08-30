package br.com.mobile10.avaliasim.util;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.mobile10.avaliasim.R;

public class BaseActivity extends AppCompatActivity {

    public void showLoadingIndicator() {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndictor() {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
    }
}
