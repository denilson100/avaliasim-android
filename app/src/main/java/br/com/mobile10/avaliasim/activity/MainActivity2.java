package br.com.mobile10.avaliasim.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.fragments.FragmentAdd;
import br.com.mobile10.avaliasim.fragments.FragmentBusca;
import br.com.mobile10.avaliasim.fragments.FragmentFavoritos;
import br.com.mobile10.avaliasim.fragments.FragmentHome;
import br.com.mobile10.avaliasim.fragments.FragmentPerfil;
import br.com.mobile10.avaliasim.helper.TabLayoutHelper;
import br.com.mobile10.avaliasim.util.BaseActivity;

public class MainActivity2 extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_star_black_24dp,
            R.drawable.ic_favorite_black_24dp,
            R.drawable.ic_person_black_24dp,
            R.drawable.ic_search_black_24dp,
            R.drawable.ic_add_black_24dp
    };

    private String[] titleToolbar = {
            "Top 100",
            "Meus Favoritos",
            "Perfil",
            "Buscar",
            "Adicionar Avaliação"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Title A", FragmentHome.class)
                .add("Title B", FragmentPerfil.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        createViewPager(viewPager);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);



        setupTabIcons();

    }


    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
//        tabLayout.getTabAt(4).setIcon(tabIcons[4]);

        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupTabIcons2() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ONE");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("TWO");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("THREE");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }


    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentHome(), "Tab 1");
        adapter.addFrag(new FragmentFavoritos(), "Tab 2");
        adapter.addFrag(new FragmentPerfil(), "Tab 3");
        adapter.addFrag(new FragmentBusca(), "Tab 4");
        adapter.addFrag(new FragmentAdd(), "Tab 5");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
