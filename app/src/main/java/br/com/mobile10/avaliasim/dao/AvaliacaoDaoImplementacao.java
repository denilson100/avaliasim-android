package br.com.mobile10.avaliasim.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.mobile10.avaliasim.interfaces.AvaliacaoDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Produto;
import br.com.mobile10.avaliasim.util.Constantes;
import br.com.mobile10.avaliasim.util.Format;

/**
 * Created by denmont on 16/04/2018.
 */

public class AvaliacaoDaoImplementacao implements AvaliacaoDao {

    private DatabaseReference mDatabase;
    String dataAtual = Format.Date(Constantes.DATA_ATUAL);

    @Override
    public void newAvaliacao(final Avaliacao2 avaliacao, final String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constantes.DB_ROOT)
                .child("avaliacaoes")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    writeNewPost(avaliacao, userId);
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


    private void writeNewPost(Avaliacao2 avaliacao, String userId) {

        String key1 = mDatabase.push().getKey();

        String key = mDatabase.child(Constantes.DB_ROOT)
                .child("avaliacoes")
                .push().getKey();
//        Avaliacao av = new Avaliacao(key1, avaliacao.title, avaliacao.type, avaliacao.author,
//                avaliacao.visible, avaliacao.cidade, avaliacao.estado, avaliacao.timeStamp, avaliacao.features);
        long timeStamp = 0;
        List<String> list = new ArrayList<String>();
        for (int i=0; i < avaliacao.features.size(); i++) {
            list.add(avaliacao.features.get(i));
        }
        Avaliacao2 av = new Avaliacao2(userId, avaliacao.cidade, avaliacao.estado, list, key1, timeStamp, avaliacao.title, avaliacao.type, true);

        Map<String, Object> postValues = av.toMap();

        Avaliacao av2 = new Avaliacao(key);
        Map<String, Object> postValuesPrivate = av2.toMap2();


        mDatabase.child(Constantes.DB_ROOT)
                .child("avaliacoes").child(key1).setValue(av);

        mDatabase.child(Constantes.DB_ROOT)
                .child("avaliacoes").child(key1).child("timeStamp").setValue(ServerValue.TIMESTAMP);

        mDatabase.child(Constantes.DB_ROOT)
                .child("users")
                .child(userId).child("myAvaliacoes").child(key1).setValue(postValuesPrivate);
    }
}
