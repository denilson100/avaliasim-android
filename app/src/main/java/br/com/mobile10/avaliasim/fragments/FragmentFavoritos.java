package br.com.mobile10.avaliasim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.Main4Activity;
import br.com.mobile10.avaliasim.activity.MainActivity;
import br.com.mobile10.avaliasim.activity.MainActivity2;

/**
 * Created by denmont on 20/04/2018.
 */

public class FragmentFavoritos extends Fragment {

    Toolbar toolbar;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FragmentFavoritos newInstance(int sectionNumber) {
        FragmentFavoritos fragment = new FragmentFavoritos();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        Main4Activity activity = (Main4Activity) getActivity();
//        activity.getSupportActionBar().setTitle("Meus Favoritos");
//        activity.setSupportActionBar(toolbar);

    }
}
