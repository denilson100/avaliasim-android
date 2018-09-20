package br.com.mobile10.avaliasim.presentation.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.presentation.fragments.BuscaFragment;
import br.com.mobile10.avaliasim.presentation.fragments.HomeFragment;
import br.com.mobile10.avaliasim.presentation.fragments.LoginFragment;
import br.com.mobile10.avaliasim.presentation.fragments.ProfileFragment;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TabLayoutContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_container_activity);

        TabLayout tabLayout = findViewById(R.id.tab_layout_container);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.preto));
        tabLayout.addTab(tabLayout.newTab().setText("Top 10").setIcon(R.drawable.ic_star_black_24dp), true);
        tabLayout.addTab(tabLayout.newTab().setText("Buscar").setIcon(R.drawable.ic_search_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("Perfil").setIcon(R.drawable.ic_person_black_24dp));
        //TODO: criar este listener em um arquivo separado
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

//                FirebaseAuth authenticator = FirebaseAuth.getInstance();
//                FirebaseUser currentUser = authenticator.getCurrentUser();

                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new HomeFragment());
                        break;
                    case 1:
                        replaceFragment(new BuscaFragment());
                        break;
                    case 2:
//                        replaceFragment(currentUser != null ? new ProfileFragment() : new LoginFragment());
                        replaceFragment(new LoginFragment());

                        break;
                }
            }

            private void replaceFragment(Fragment fragment) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(0).getIcon().clearColorFilter();
                tab.getIcon().clearColorFilter();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Forçando evento onTabSelected para criação do fragment
        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }
}