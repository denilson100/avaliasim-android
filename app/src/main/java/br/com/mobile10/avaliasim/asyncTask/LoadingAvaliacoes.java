package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mobile10.avaliasim.activity.HomeActivity2;
import br.com.mobile10.avaliasim.activity.Main4Activity;
import br.com.mobile10.avaliasim.fragments.FragmentHome;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.Constantes;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingAvaliacoes extends AsyncTask<Void, Void, List<Avaliacao2>> {

    private Main4Activity activity2;
    private FragmentHome activity;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.DB_ROOT).child("avaliacoes");
    private Avaliacao avaliacao = new Avaliacao();
    private List<Avaliacao2> avaliacao2ListFinal = new ArrayList<Avaliacao2>();
//    private Feature feature = new Feature();
    private Avaliacao2 avaliacao2;

//    public LoadingAvaliacoes(HomeActivity2 activity) {
//        this.activity = activity;
//
//    }
public LoadingAvaliacoes(Main4Activity activity2) {
    this.activity2 = activity2;
}

    public LoadingAvaliacoes(FragmentHome fragmentHome) {
        this.activity = fragmentHome;
    }

    @Override
    protected List<Avaliacao2> doInBackground(Void... voids) {

        final List<Avaliacao> listavaliacoes = new ArrayList<Avaliacao>();

        final List<Feature> listaAvaliacoes = new ArrayList<Feature>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Avaliacao2> avaliacao2List = new ArrayList<Avaliacao2>();

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

                        int key = Integer.parseInt(ft.getKey());
                        String value = ft.getValue().toString();
                        listNamesFeatures.add(value);

                    }

                    List<Feature> featureList = new ArrayList<Feature>();
                    for (DataSnapshot list : av.child("listAvaliacoes").getChildren()) {

                        String featureName = list.getKey();

                        List<MyDate> listDate = new ArrayList<MyDate>();
                        for (DataSnapshot data : list.getChildren()) {

                            String stringDate = list.getKey();
                            int positive = (int) data.child("positive").getChildrenCount();
                            int negative = (int) data.child("negative").getChildrenCount();

                            MyDate myDate1 = new MyDate(positive, negative);
                            listDate.add(myDate1);

                        }

                        Feature feature = new Feature(featureName, listDate);
                        featureList.add(feature);

                    }

//                    String[] stringArray = listNamesFeatures.toArray(new String[0]);
                    Avaliacao2 av2 = new Avaliacao2(author, cidade, estado, listNamesFeatures, idAvaliacao, timeStamp, title, type, visible, featureList);
                    avaliacao2ListFinal.add(av2);
                }

                onPostExecute(avaliacao2ListFinal);
                activity.hideLoadingIndictor();
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
        activity.showLoadingIndicator();
    }

    @Override
    protected void onPostExecute(List<Avaliacao2> itemList) {
        super.onPostExecute(itemList);
        Collections.sort(itemList);
        activity.setListAvaliacoes(itemList);
    }
}
