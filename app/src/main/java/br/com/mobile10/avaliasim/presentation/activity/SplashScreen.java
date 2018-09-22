package br.com.mobile10.avaliasim.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import br.com.mobile10.avaliasim.R;

public class SplashScreen extends AppCompatActivity {

    private ImageView imgWordV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgWordV = (ImageView) findViewById(R.id.word_v);
        imgWordV.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, TabLayoutContainerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }

}