package br.com.mobile10.avaliasim.presentation.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.ClockDAO;
import br.com.mobile10.avaliasim.data.dao.RatingDAO;
import br.com.mobile10.avaliasim.model.Rating;
import br.com.mobile10.avaliasim.presentation.activity.NewRatingActivity;
import br.com.mobile10.avaliasim.presentation.activity.TabLayoutContainerActivity;
import br.com.mobile10.avaliasim.presentation.adapter.RecyclerViewAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by denmont on 20/04/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton rateBtn;

    private FirebaseUser loggedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        initializeVariables();
        initializeViews(rootView);

        new RatingDAO().findAll(this::loadRatingCards);
        new ClockDAO().synchronizeClock();

        return rootView;
    }

    private void initializeVariables() {
        loggedUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.cards_recycler_view);
        viewSwitcher = view.findViewById(R.id.cards_switcher);
        rateBtn = view.findViewById(R.id.rate_btn);
        swipeLayout = view.findViewById(R.id.swipe_container);

        rateBtn.setOnClickListener(this::onRateBtnClick);
        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    private void onRateBtnClick(View view) {
        if (loggedUser != null)
            startActivity(new Intent(getActivity(), NewRatingActivity.class));
        else {
            TabLayout tabLayout = ((TabLayoutContainerActivity) getActivity()).findViewById(R.id.tab_layout_container);
            tabLayout.getTabAt(2).select();
            Toast.makeText(getActivity(), "Você precisa estar logado para criar uma avaliação", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRefreshSwipe() {
        recyclerView.setAdapter(recyclerViewAdapter);
        swipeLayout.setRefreshing(false);
    }

    public void loadRatingCards(Object result) {
        List<Rating> listAvaliacoes = (List<Rating>) result;
        recyclerViewAdapter = new RecyclerViewAdapter(listAvaliacoes);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        viewSwitcher.showNext();
    }
}