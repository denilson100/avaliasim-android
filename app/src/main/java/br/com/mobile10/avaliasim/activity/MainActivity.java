package br.com.mobile10.avaliasim.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.fragments.FragmentAdd;
import br.com.mobile10.avaliasim.fragments.FragmentBusca;
import br.com.mobile10.avaliasim.fragments.FragmentFavoritos;
import br.com.mobile10.avaliasim.fragments.FragmentHome;
import br.com.mobile10.avaliasim.fragments.FragmentPerfil;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favoritos:
                    fragment = new FragmentFavoritos();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_top:
                    fragment = new FragmentHome();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_add:
                    fragment = new FragmentAdd();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_perfil:
                    fragment = new FragmentPerfil();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_busca:
                    fragment = new FragmentBusca();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragment = new FragmentHome();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }

}
