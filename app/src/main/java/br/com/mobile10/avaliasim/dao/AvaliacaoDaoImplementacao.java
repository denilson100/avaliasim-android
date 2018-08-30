package br.com.mobile10.avaliasim.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.mobile10.avaliasim.interfaces.AvaliacaoDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.util.Constants;

/**
 * Created by denmont on 16/04/2018.
 */

public class AvaliacaoDaoImplementacao implements AvaliacaoDao {

    private DatabaseReference mDatabase;

    @Override
    public void newAvaliacao(final Avaliacao2 avaliacao, final String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constants.DB_ROOT)
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
        String key = mDatabase.child(Constants.DB_ROOT)
                .child("avaliacoes")
                .push().getKey();

        long timeStamp = 0;
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < avaliacao.features.size(); i++)
            list.add(avaliacao.features.get(i));

        Avaliacao2 av = new Avaliacao2(userId, avaliacao.cidade, avaliacao.estado, list, key, timeStamp, avaliacao.title, avaliacao.type, true);

        Avaliacao av2 = new Avaliacao(key);
        Map<String, Object> postValuesPrivate = av2.toMap2();

        mDatabase.child(Constants.DB_ROOT)
                .child("avaliacoes").child(key).setValue(av);

        mDatabase.child(Constants.DB_ROOT)
                .child("avaliacoes").child(key).child("timeStamp").setValue(ServerValue.TIMESTAMP);

        mDatabase.child(Constants.DB_ROOT)
                .child("users")
                .child(userId).child("myAvaliacoes").child(key).setValue(postValuesPrivate);
    }
}
