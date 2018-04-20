package br.com.mobile10.avaliasim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingDataAtual;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.dao.DataAtualDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.DataAtualDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Produto;
import br.com.mobile10.avaliasim.util.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.OnItemClicked,
        DataAtualDao {

    List<Avaliacao2> avaliacoesList = new ArrayList<Avaliacao2>();
    private RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapter rcAdapter;
    RecyclerView rView;

    private FirebaseAuth mAuth;
    FirebaseUser users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        lLayout = new LinearLayoutManager(this);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        executeAsyncTaskGetDataAtual();
        addDataAtualNoServidor();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(users != null) {
                    Intent intent = new Intent(HomeActivity.this, NewAvaliacao.class);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(HomeActivity.this, EmailPasswordActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void executeAsyncTaskGetDataAtual() {
        new LoadingDataAtual(this).execute();
    }

    private void executeAsyncTaskGetAvaliacoes() {
        new LoadingAvaliacoes(this).execute();
    }

    public void setListAvaliacoes(List<Avaliacao2> listAvaliacoes) {
        this.avaliacoesList = listAvaliacoes;
        atualizarLista(listAvaliacoes);
    }

    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
        LinearLayout imgVazio = (LinearLayout) findViewById(R.id.vazio);
        try {
            rcAdapter = new RecyclerViewAdapter(this, listAvaliacoes);
            if (listAvaliacoes.size() != 0) {
                imgVazio.setVisibility(View.GONE);
            } else {
                imgVazio.setVisibility(View.VISIBLE);
            }
            rView.setItemAnimator(new SlideInUpAnimator());
            rView.setAdapter(rcAdapter);
            rcAdapter.setOnClick(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
            showToast("Erro");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        executeAsyncTaskGetAvaliacoes();
    }

    @Override
    public void onItemClick(int position) {

        Avaliacao2 av = new Avaliacao2(
                avaliacoesList.get(position).author,
                avaliacoesList.get(position).cidade,
                avaliacoesList.get(position).estado,
                avaliacoesList.get(position).features,
                avaliacoesList.get(position).idAvaliacao,
                avaliacoesList.get(position).timeStemp,
                avaliacoesList.get(position).title,
                avaliacoesList.get(position).type,
                avaliacoesList.get(position).visible,
                avaliacoesList.get(position).listaAvaliacoes);

        Intent intent = new Intent(this, DetelhesAvaliacao.class);
        intent.putExtra("avaliacao", av);
        startActivity(intent);
    }

    public void logicaUsuario() {
        if (users != null) {
            Intent intent = new Intent(this, AvaliacaActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void addDataAtualNoServidor() {
        DataAtualDaoImplementacao dao = new DataAtualDaoImplementacao();
        dao.addDataAtualNoServidor();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            try {
                mAuth.signOut();
            } catch (Exception e) {

            }
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
