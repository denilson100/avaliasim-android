package br.com.mobile10.avaliasim.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.fragments.HomeFragment;
import br.com.mobile10.avaliasim.util.BaseActivity;

public class HomeActivity extends BaseActivity  {

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragment_containers, new HomeFragment());
        transaction.commit();
    }

}
