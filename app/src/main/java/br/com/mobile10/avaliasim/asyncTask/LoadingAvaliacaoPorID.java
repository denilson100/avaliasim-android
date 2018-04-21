package br.com.mobile10.avaliasim.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.activity.DetalhesAvaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Feature;
import br.com.mobile10.avaliasim.modelo.MyDate;
import br.com.mobile10.avaliasim.util.Constantes;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingAvaliacaoPorID extends AsyncTask<Void, Void, Avaliacao2> {

    private DetalhesAvaliacao activity;
    private String idDaBusca;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.DB_ROOT).child("avaliacoes");
    private Avaliacao2 avaliacao2;

    public LoadingAvaliacaoPorID(DetalhesAvaliacao activity, String idDaBusca) {
        this.activity = activity;
        this.idDaBusca = idDaBusca;

    }

    @Override
    protected Avaliacao2 doInBackground(Void... voids) {

        final List<Avaliacao> listavaliacoes = new ArrayList<Avaliacao>();

        final List<Feature> listaAvaliacoes = new ArrayList<Feature>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Avaliacao2> avaliacao2List = new ArrayList<Avaliacao2>();

                for (DataSnapshot av : dataSnapshot.getChildren()) {

                    if (idDaBusca.equalsIgnoreCase(av.getKey())) {

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
                        avaliacao2 = new Avaliacao2(author, cidade, estado, listNamesFeatures, idAvaliacao, timeStamp, title, type, visible, featureList);
                    }
                }

                onPostExecute(avaliacao2);
                activity.hideLoadingIndictor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return avaliacao2;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showLoadingIndicator();
    }

    @Override
    protected void onPostExecute(Avaliacao2 item) {
        super.onPostExecute(item);
        activity.setAvaliacaoPorId(item);
    }
}
