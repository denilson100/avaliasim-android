package br.com.mobile10.avaliasim.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.mobile10.avaliasim.interfaces.FeatureDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Voto;
import br.com.mobile10.avaliasim.util.Constantes;
import br.com.mobile10.avaliasim.util.Format;

/**
 * Created by denmont on 16/04/2018.
 */

public class FeatureDaoImplementacao implements FeatureDao {

    private DatabaseReference mDatabase;
    String dataAtual = Format.Date(Constantes.DATA_ATUAL);

    @Override
    public void avaliarFeatures(final Avaliacao2 avaliacao, final String userId, final String status, final String featureAvaliada) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constantes.DB_ROOT)
                .child("avaliacaoes")
                .child(dataAtual)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    writeNewPost(avaliacao, userId, status, featureAvaliada);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("TAG", "Erro");

                            }
                        });
    }


    private void writeNewPost(Avaliacao2 avaliacao, String userId, String status, String featureAvaliada) {
        String key = mDatabase.child(Constantes.DB_ROOT).child("avaliacoes").child(avaliacao.idAvaliacao).child("listAvaliacoes").child(featureAvaliada).child(dataAtual).child(status).child(userId).getKey();
        Voto voto = new Voto(userId);
        Map<String, Object> postValuesVoto = voto.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        String caminhoPrivado = "/" + Constantes.DB_ROOT + "/avaliacoes/" + avaliacao.idAvaliacao + "/listAvaliacoes/" + featureAvaliada + "/" + dataAtual + "/" + status + "/";
        childUpdates.put(caminhoPrivado + key, postValuesVoto);

        mDatabase.updateChildren(childUpdates);
    }
}
