package br.com.mobile10.avaliasim.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.github.fabtransitionactivity.SheetLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.DetalhesAvaliacao;
import br.com.mobile10.avaliasim.activity.MainActivity;
import br.com.mobile10.avaliasim.activity.MainActivity2;
import br.com.mobile10.avaliasim.activity.NewAvaliacao;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingDataAtual;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.dao.DataAtualDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.DataAtualDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by denmont on 20/04/2018.
 */

public class FragmentHome extends Fragment implements DataAtualDao, RecyclerViewAdapter.OnItemClicked {

    List<Avaliacao2> avaliacoesList = new ArrayList<Avaliacao2>();
    public RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapter rcAdapter;
    RecyclerView rView;
    public FloatingActionButton fab;
    View loadingIndicator;
    View imgVazio;
    FloatingActionButton btNovaAvaliacao;
//    SheetLayout mSheetLayout;

    Toolbar toolbar;
    private FirebaseAuth mAuth;
    FirebaseUser users;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int REQUEST_CODE = 1;
    private SwipeRefreshLayout swipeLayout;

    public static FragmentHome newInstance(int sectionNumber) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        executeAsyncTaskGetAvaliacoes();
//
//        Log.d("TAG", "OnResume Home");
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        loadingIndicator = (View) rootView.findViewById(R.id.loading_indicator);
        imgVazio = (View) rootView.findViewById(R.id.vazio);
//        mSheetLayout = (SheetLayout) rootView.findViewById(R.id.bottom_sheet);

        btNovaAvaliacao = (FloatingActionButton) rootView.findViewById(R.id.fab);
        btNovaAvaliacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (users != null) {
                    Intent intent2 = new Intent(getActivity(), NewAvaliacao.class);
                    startActivity(intent2);
//                    mSheetLayout.expandFab();

                } else {
                    Intent intent2 = new Intent(getActivity(), EmailPasswordActivity.class);
                    startActivity(intent2);
                }
            }
        });

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                executeAsyncTaskGetAvaliacoes();
                swipeLayout.setRefreshing(false);
            }
        });


        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setHasFixedSize(true);
//        rView.setLayoutManager(lLayout);

        executeAsyncTaskGetDataAtual();
        executeAsyncTaskGetAvaliacoes();
        addDataAtualNoServidor();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

    }

    private void executeAsyncTaskGetDataAtual() {
        new LoadingDataAtual(this).execute();
    }

    private void executeAsyncTaskGetAvaliacoes() {
        new LoadingAvaliacoes(this).execute();
    }

    public void setListAvaliacoes(List<Avaliacao2> listAvaliacoes) {
        this.avaliacoesList = listAvaliacoes;
        if (listAvaliacoes.size() != 0)
            atualizarLista(listAvaliacoes);
    }

    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
        try {
            rcAdapter = new RecyclerViewAdapter(getActivity(), listAvaliacoes);
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
//            getActivity().showToast("Erro");
        }
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

        Intent intent = new Intent(getActivity(), DetalhesAvaliacao.class);
        intent.putExtra("avaliacao", av);
        startActivity(intent);
    }

    @Override
    public void addDataAtualNoServidor() {
        DataAtualDaoImplementacao dao = new DataAtualDaoImplementacao();
        dao.addDataAtualNoServidor();
    }

    public void showLoadingIndicator() {
        imgVazio.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndictor() {
        imgVazio.setVisibility(View.GONE);
    }

//    public void showLoadingIndicator() {
//        loadingIndicator.setVisibility(View.VISIBLE);
//    }
//
//    public void hideLoadingIndictor() {
//        loadingIndicator.setVisibility(View.GONE);
//    }

}
