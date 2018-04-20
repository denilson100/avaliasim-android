package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.activity.DetelhesAvaliacao;
import br.com.mobile10.avaliasim.activity.HomeActivity;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.util.Constantes;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingAvaliacoesGetTotal extends AsyncTask<Void, Void, String> {

    private DetelhesAvaliacao activity;
    private Avaliacao2 avaliacao;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.DB_ROOT).child("avaliacoes");
//    private Feature feature = new Feature();
    private String total;
    private int result;

    public LoadingAvaliacoesGetTotal(DetelhesAvaliacao activity, Avaliacao2 avaliacao) {
        this.activity = activity;
        this.avaliacao = avaliacao;

    }

    @Override
    protected String doInBackground(Void... voids) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //id avaliacao
                for (DataSnapshot av : dataSnapshot.getChildren()) {

                    if (avaliacao.idAvaliacao.equalsIgnoreCase(av.getKey())) {

                        for (DataSnapshot list : av.child("listAvaliacoes").getChildren()) {

                            for (DataSnapshot feature : list.getChildren()) {

                                result += (int) feature.child("positive").getChildrenCount();
                                result += (int) feature.child("negative").getChildrenCount();
                                total = "Total: " + result + " avaliações";

                            }

                        }

                    }

                }

                onPostExecute(total);
                activity.hideLoadingIndictor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return total;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showLoadingIndicator();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        activity.setAvaliacoesTotal(result);
    }
}
