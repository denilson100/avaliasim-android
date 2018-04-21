package br.com.mobile10.avaliasim.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingDataAtual;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.dao.DataAtualDaoImplementacao;
import br.com.mobile10.avaliasim.fragments.FragmentHome;
import br.com.mobile10.avaliasim.interfaces.DataAtualDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class HomeActivity2 extends BaseActivity  {

    List<Avaliacao2> avaliacoesList = new ArrayList<Avaliacao2>();
    private RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapter rcAdapter;
    RecyclerView rView;

    private FirebaseAuth mAuth;
    FirebaseUser users;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragment_containers, new FragmentHome());
        transaction.commit();



//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        users = user;
//
//        lLayout = new LinearLayoutManager(this);
//        rView = (RecyclerView) findViewById(R.id.recycler_view);
//        rView.setHasFixedSize(true);
//        rView.setLayoutManager(lLayout);
//
//        executeAsyncTaskGetDataAtual();
//        executeAsyncTaskGetAvaliacoes();
//        addDataAtualNoServidor();
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(users != null) {
//                    Intent intent = new Intent(HomeActivity2.this, NewAvaliacao.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent1 = new Intent(HomeActivity2.this, EmailPasswordActivity.class);
//                    startActivity(intent1);
//                    finish();
//                }
//            }
//        });

        BottomNavigation bottomNavigation=(BottomNavigation)findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {
                switch (itemId){
                    case R.id.tab_favoritos:
                        break;
                    case R.id.tab_perfil:
                        break;
                    case R.id.tab_busca:
                        break;
                    case R.id.tab_top_avaliados:
                        break;
                }
            }
        });

    }

//    private void executeAsyncTaskGetAvaliacoes() {
//        new LoadingAvaliacoes(this).execute();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        executeAsyncTaskGetAvaliacoes();
//    }

//    private void executeAsyncTaskGetDataAtual() {
//        new LoadingDataAtual(this).execute();
//    }
//
//    private void executeAsyncTaskGetAvaliacoes() {
//        new LoadingAvaliacoes(this).execute();
//    }
//
//    public void setListAvaliacoes(List<Avaliacao2> listAvaliacoes) {
//        this.avaliacoesList = listAvaliacoes;
//        atualizarLista(listAvaliacoes);
//    }
//
//    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
//        LinearLayout imgVazio = (LinearLayout) findViewById(R.id.vazio);
//        try {
//            rcAdapter = new RecyclerViewAdapter(this, listAvaliacoes);
//            if (listAvaliacoes.size() != 0) {
//                imgVazio.setVisibility(View.GONE);
//            } else {
//                imgVazio.setVisibility(View.VISIBLE);
//            }
//            rView.setItemAnimator(new SlideInUpAnimator());
//            rView.setAdapter(rcAdapter);
//            rcAdapter.setOnClick(this);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            showToast("Erro");
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        executeAsyncTaskGetAvaliacoes();
//    }
//
//    @Override
//    public void onItemClick(int position) {
//
//        Avaliacao2 av = new Avaliacao2(
//                avaliacoesList.get(position).author,
//                avaliacoesList.get(position).cidade,
//                avaliacoesList.get(position).estado,
//                avaliacoesList.get(position).features,
//                avaliacoesList.get(position).idAvaliacao,
//                avaliacoesList.get(position).timeStemp,
//                avaliacoesList.get(position).title,
//                avaliacoesList.get(position).type,
//                avaliacoesList.get(position).visible,
//                avaliacoesList.get(position).listaAvaliacoes);
//
//        Intent intent = new Intent(this, DetelhesAvaliacao.class);
//        intent.putExtra("avaliacao", av);
//        startActivity(intent);
//    }
//
//    public void logicaUsuario() {
//        if (users != null) {
//            Intent intent = new Intent(this, AvaliacaActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(this, EmailPasswordActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    @Override
//    public void addDataAtualNoServidor() {
//        DataAtualDaoImplementacao dao = new DataAtualDaoImplementacao();
//        dao.addDataAtualNoServidor();
//    }

}
