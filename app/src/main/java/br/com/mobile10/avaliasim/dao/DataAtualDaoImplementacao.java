package br.com.mobile10.avaliasim.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.mobile10.avaliasim.interfaces.DataAtualDao;
import br.com.mobile10.avaliasim.modelo.DataAtual;
import br.com.mobile10.avaliasim.util.Constantes;

/**
 * Created by denmont on 16/04/2018.
 */

public class DataAtualDaoImplementacao implements DataAtualDao {

    private DatabaseReference mDatabase;

    @Override
    public void addDataAtualNoServidor() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child(Constantes.DB_ROOT).child("dataAtual").getKey();
        DataAtual dataAtual = new DataAtual();
        Map<String, Object> postValues = dataAtual.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        String caminho = "/" + Constantes.DB_ROOT + "/";
        childUpdates.put(caminho + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

}
