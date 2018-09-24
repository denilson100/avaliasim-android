package br.com.mobile10.avaliasim.presentation.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.presentation.fragments.DeliverableCardsFragment;
import br.com.mobile10.avaliasim.presentation.fragments.LoginFragment;
import br.com.mobile10.avaliasim.presentation.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.presentation.fragments.RatingSearchFragment;
import br.com.mobile10.avaliasim.util.InterfaceUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TabLayoutContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_container_activity);

        IUserDAO userDAO = new UserDAO();
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
                switch (tab.getPosition()) {
                    case 0:
                        InterfaceUtils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, new DeliverableCardsFragment());
                        break;
                    case 1:
                        InterfaceUtils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, new RatingSearchFragment());
                        break;
                    case 2:
                        InterfaceUtils.replaceFragment(getSupportFragmentManager(), R.id.frame_container,
                                userDAO.getLoggedUser() != null ? new ProfileFragment() : new LoginFragment());
                        break;
                }
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