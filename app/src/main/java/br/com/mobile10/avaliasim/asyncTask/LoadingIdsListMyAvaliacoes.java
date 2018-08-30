package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.activity.Main4Activity;
import br.com.mobile10.avaliasim.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.Constantes;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingIdsListMyAvaliacoes extends AsyncTask<Void, Void, List<String>> {

    private Main4Activity activity;
    private ProfileFragment fragmentPerfil;
    private String userId;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.DB_ROOT).child("users");
    private Avaliacao2 avaliacao2;
    private List<String> list = new ArrayList<>();

    public LoadingIdsListMyAvaliacoes(ProfileFragment fragmentPerfil, String userId) {
        this.fragmentPerfil = fragmentPerfil;
        this.userId = userId;

    }

    @Override
    protected List<String> doInBackground(Void... voids) {

        ref.child(userId).child("myAvaliacoes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    String id = (String) snap.child("idAvaliacao").getValue();
                    list.add(id);

                }

                onPostExecute(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<String> item) {
        super.onPostExecute(item);
//        fragmentPerfil.getListIdMyAvaliacoes(item);
    }
}
