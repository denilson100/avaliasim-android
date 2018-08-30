package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.Constants;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingMyAvaliacoes extends AsyncTask<Void, Void, List<Avaliacao2>> {

    private ProfileFragment fragmentPerfil;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("avaliacoes");
    private List<Avaliacao2> avaliacao2ListFinal = new ArrayList<Avaliacao2>();
    private List<String> idsList;

    public LoadingMyAvaliacoes(ProfileFragment fragmentPerfil, List<String> idsList) {
        this.fragmentPerfil = fragmentPerfil;
        this.idsList = idsList;
    }

    @Override
    protected List<Avaliacao2> doInBackground(Void... voids) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot av : dataSnapshot.getChildren()) {

                    String idAvaliacao = av.getKey();
                    String author = (String) av.child("author").getValue();
                    String cidade = (String) av.child("cidade").getValue();
                    String estado = (String) av.child("estado").getValue();
                    long timeStamp = (long) av.child("timeStamp").getValue();
                    String title = (String) av.child("title").getValue();
                    String type = (String) av.child("type").getValue();
                    boolean visible = (boolean) av.child("visible").getValue();

                    List<String> listNamesFeatures = new ArrayList<String>();
                    for (DataSnapshot ft : av.child("features").getChildren()) {
                        String value = ft.getValue().toString();
                        listNamesFeatures.add(value);
                    }

                    List<Feature> featureList = new ArrayList<Feature>();
                    for (DataSnapshot list : av.child("listAvaliacoes").getChildren()) {

                        String featureName = list.getKey();

                        List<MyDate> listDate = new ArrayList<MyDate>();
                        for (DataSnapshot data : list.getChildren()) {

                            int positive = (int) data.child("positive").getChildrenCount();
                            int negative = (int) data.child("negative").getChildrenCount();

                            MyDate myDate1 = new MyDate(positive, negative);
                            listDate.add(myDate1);

                        }

                        Feature feature = new Feature(featureName, listDate);
                        featureList.add(feature);

                    }

                    Avaliacao2 av2 = new Avaliacao2(author, cidade, estado, listNamesFeatures, idAvaliacao, timeStamp, title, type, visible, featureList);
                    avaliacao2ListFinal.add(av2);
                }

                onPostExecute(avaliacao2ListFinal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return avaliacao2ListFinal;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Avaliacao2> itemList) {
        super.onPostExecute(itemList);
//        fragmentPerfil.setListMyAvaliacoes(itemList);
    }
}
