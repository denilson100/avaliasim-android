package br.com.mobile10.avaliasim.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import br.com.mobile10.avaliasim.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        ImageView v = findViewById(R.id.word_v);
        new Handler().postDelayed(() -> {
            v.setImageResource(R.drawable.logo_v);
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));
        }, 800);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, TabLayoutContainerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }

}