package br.com.mobile10.avaliasim.presentation.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.ClockDAO;
import br.com.mobile10.avaliasim.data.dao.RatingDAO;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.data.interfaces.IClockDAO;
import br.com.mobile10.avaliasim.data.interfaces.IRatingDAO;
import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
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

    private IClockDAO clockDAO;
    private IUserDAO userDAO;
    private IRatingDAO ratingDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        initializeViews(rootView);

        clockDAO = new ClockDAO();
        userDAO = new UserDAO();
        ratingDAO = new RatingDAO();

        ratingDAO.findAll(this::loadRatingCards);
        clockDAO.synchronizeClock();

        return rootView;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.cards_recycler_view);
        viewSwitcher = view.findViewById(R.id.cards_switcher);
        swipeLayout = view.findViewById(R.id.swipe_container);

        view.findViewById(R.id.rate_btn).setOnClickListener(this::onRateBtnClick);
        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    private void onRateBtnClick(View view) {
        if (userDAO.getLoggedUser() != null)
            startActivity(new Intent(getActivity(), NewRatingActivity.class));
        else {
            TabLayout tabLayout = ((TabLayoutContainerActivity) getActivity()).findViewById(R.id.tab_layout_container);
            tabLayout.getTabAt(2).select();
            Toast.makeText(getActivity(), "Você precisa estar logado para criar uma avaliação", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRefreshSwipe() {
        ratingDAO.findAll(result -> recyclerView.setAdapter(new RecyclerViewAdapter((List<Rating>) result)));
        swipeLayout.setRefreshing(false);
    }

    public void loadRatingCards(Object result) {
        List<Rating> listAvaliacoes = (List<Rating>) result;
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(listAvaliacoes));
        viewSwitcher.showNext();
    }
}