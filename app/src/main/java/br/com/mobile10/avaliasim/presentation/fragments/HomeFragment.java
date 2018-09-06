package br.com.mobile10.avaliasim.presentation.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.model.Avaliacao;
import br.com.mobile10.avaliasim.model.Avaliacao2;
import br.com.mobile10.avaliasim.presentation.activity.NewAvaliacao;
import br.com.mobile10.avaliasim.presentation.activity.TabLayoutContainerActivity;
import br.com.mobile10.avaliasim.presentation.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.util.Constants;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by denmont on 20/04/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    List<Avaliacao2> avaliacoesList = new ArrayList<Avaliacao2>();
    private SwipeRefreshLayout swipeLayout;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter rcAdapter;
    private FloatingActionButton rateBtn;
    private FirebaseAuth authenticator;
    private FirebaseUser loggedUser;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public HomeFragment() {
        this.setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        authenticator = FirebaseAuth.getInstance();
        loggedUser = authenticator.getCurrentUser();
        initializeViews(rootView);

        Avaliacao av = new Avaliacao();
        av.author = "Ricardo";
        av.city = "rj";
        av.isVisible = true;
        av.ratingId = "2121";
        av.state = "RJ";
        av.timestamp = 2312324L;
        av.title = "eoka";
        av.type = "ORGM";

        Avaliacao av2 = new Avaliacao();
        BeanUtils.copyProperties(av, av2);


//        CodeUtils.synchronizeClock();
//        executeAsyncTaskGetAvaliacoes();
//        addDataAtualNoServidor();

        return rootView;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewSwitcher = view.findViewById(R.id.cards_switcher);
        rateBtn = view.findViewById(R.id.rate_btn);
        swipeLayout = view.findViewById(R.id.swipe_container);

        rateBtn.setOnClickListener(this::onRateBtnClick);
        swipeLayout.setOnRefreshListener(this::onRefreshSwipe);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setHasFixedSize(true);
    }

    private void onRateBtnClick(View view) {
        if (loggedUser != null)
            startActivity(new Intent(getActivity(), NewAvaliacao.class));
    }

    private void onRefreshSwipe() {
//        executeAsyncTaskGetAvaliacoes();
//        swipeLayout.setRefreshing(false);
    }

    private TabLayoutContainerActivity activity2;
    private HomeFragment activity;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("avaliacoes");
    private List<Avaliacao2> avaliacao2ListFinal = new ArrayList<Avaliacao2>();

    //TODO: reformular este método
    private void executeAsyncTaskGetAvaliacoes() {
//        new LoadingAvaliacoes(this).execute();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


//                for (DataSnapshot av : dataSnapshot.getChildren()) {
//                    String ratingId = av.getKey();
//                    String author = av.child("author").getValue().toString();
//                    String city = av.child("cidade").getValue().toString();
//                    String state = av.child("estado").getValue().toString();
//                    String title = av.child("title").getValue().toString();
//                    String type = av.child("type").getValue().toString();
//                    long timeStamp = Long.valueOf(av.child("timeStamp").getValue().toString());
//                    boolean visible = Boolean.valueOf(av.child("visible").getValue().toString());
//
//                    List<Feature> featureList = new ArrayList<Feature>();
//                    List<String> listNamesFeatures = new ArrayList<>();
//
//                    Avaliacao2 av2 = new Avaliacao2(author, city, state, listNamesFeatures, ratingId, timeStamp, title, type, visible, featureList);
//                    avaliacao2ListFinal.add(av2);
//
//                    av.child("features").getChildren().forEach(feature -> listNamesFeatures.add(feature.getValue().toString()));
//
//                    av.child("listAvaliacoes").getChildren().forEach(listaAvaliacoes -> {
//                        String featureName = listaAvaliacoes.getKey();
//                        List<MyDate> listDate = new ArrayList<MyDate>();
//
//                        listaAvaliacoes.getChildren().forEach(data -> {
//                            DateTimeFormatter brazilianDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                            LocalDateTime localDateTime = LocalDateTime.parse(data.getKey(), brazilianDateFormatter);
//
//                            int positive = (int) data.child("positive").getChildrenCount();
//                            int negative = (int) data.child("negative").getChildrenCount();
//
//                            MyDate myDate1 = new MyDate(positive, negative, localDateTime);
//                            listDate.add(myDate1);
//                        });
//
//                        Feature feature = new Feature(featureName, listDate);
//                        featureList.add(feature);
//                    });

//
//                    finished(avaliacao2ListFinal);
//                    activity.hideLoadingIndictor();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ref.addListenerForSingleValueEvent(dataSnapshot -> {


//
//                        }
//        Avaliacao2 av2 = new Avaliacao2(author, cidade, estado, listNamesFeatures, idAvaliacao, timeStamp, title, type, visible, featureList);
//        avaliacao2ListFinal.add(av2);
//    }
//
//    finished(avaliacao2ListFinal);
//                activity.hideLoadingIndictor();


//                    protected void finished(List<Avaliacao2> itemList) {
//                        Collections.sort(itemList);
//                        activity.setListAvaliacoes(itemList);
//                    }


    }

    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
        try {
            rcAdapter = new RecyclerViewAdapter(getActivity(), listAvaliacoes);

            recyclerView.setItemAnimator(new SlideInUpAnimator());
            recyclerView.setAdapter(rcAdapter);
//            rcAdapter. (this::onItemClick);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


//    public void onItemClick(int position) {
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
//        Intent intent = new Intent(getActivity(), DetalhesAvaliacao.class);
//        intent.putExtra("avaliacao", av);
//        startActivity(intent);
//    }

//    @Override
//    public void addDataAtualNoServidor() {
//        DataAtualDaoImplementacao dao = new DataAtualDaoImplementacao();
//        dao.addDataAtualNoServidor();
//    }
}