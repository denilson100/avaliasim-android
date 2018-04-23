package br.com.mobile10.avaliasim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.Main4Activity;

/**
 * Created by denmont on 20/04/2018.
 */

public class FragmentPerfil extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FragmentPerfil newInstance(int sectionNumber) {
        FragmentPerfil fragment = new FragmentPerfil();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
