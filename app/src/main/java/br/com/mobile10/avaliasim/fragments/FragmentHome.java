package br.com.mobile10.avaliasim.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.DetalhesAvaliacao;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.asyncTask.LoadingAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingDataAtual;
import br.com.mobile10.avaliasim.dao.DataAtualDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.DataAtualDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
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

    private FirebaseAuth mAuth;
    FirebaseUser users;


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        users = user;
//
//        executeAsyncTaskGetDataAtual();
//        executeAsyncTaskGetAvaliacoes();
//        addDataAtualNoServidor();
//
////        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                if(users != null) {
////                    Intent intent = new Intent(getActivity(), NewAvaliacao.class);
////                    startActivity(intent);
////                } else {
////                    Intent intent1 = new Intent(getActivity(), EmailPasswordActivity.class);
////                    startActivity(intent1);
////                    getActivity().finish();
////                }
////            }
////        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        executeAsyncTaskGetAvaliacoes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        loadingIndicator = (View) rootView.findViewById(R.id.loading_indicator);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        executeAsyncTaskGetDataAtual();
        executeAsyncTaskGetAvaliacoes();
        addDataAtualNoServidor();

//        loadingIndicator = (View) getActivity().findViewById(R.id.loading_indicator);

        rView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        // 2. set layoutManger
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        lLayout = new LinearLayoutManager(getActivity());
//        rView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
//        rView.setLayoutManager(lLayout);
//        atualizarLista(avaliacoesList);


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
        LinearLayout imgVazio = (LinearLayout) getActivity().findViewById(R.id.vazio);
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
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndictor() {
        loadingIndicator.setVisibility(View.GONE);
    }

}