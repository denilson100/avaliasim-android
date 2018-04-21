package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.activity.HomeActivity2;
import br.com.mobile10.avaliasim.fragments.FragmentHome;
import br.com.mobile10.avaliasim.util.Constantes;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingDataAtual extends AsyncTask<Void, Void, Long> {

//    private HomeActivity2 activity;
    private FragmentHome activity;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.DB_ROOT).child("dataAtual");
    Long timeStamp;

//    public LoadingDataAtual(HomeActivity2 activity) {
//        this.activity = activity;
//
//    }

    public LoadingDataAtual(FragmentHome fragmentHome) {
        this.activity = fragmentHome;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    timeStamp = (Long) dataSnapshot.child("timeStamp").getValue();

                } catch (ClassCastException e){
                    // Captura a excecao caso tenha algum dado inesperado. Tipo latitude == 0.
                    e.printStackTrace();
                }

                activity.hideLoadingIndictor();
                Constantes.DATA_ATUAL = timeStamp;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return timeStamp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showLoadingIndicator();
    }

    @Override
    protected void onPostExecute(Long timeStamp) {
        super.onPostExecute(timeStamp);
    }
}
