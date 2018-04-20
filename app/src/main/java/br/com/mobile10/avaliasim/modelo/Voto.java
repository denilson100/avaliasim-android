package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denmont on 17/04/2018.
 */

@IgnoreExtraProperties
public class Voto {
    public String id;

    public Voto(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("timeStamp", ServerValue.TIMESTAMP);

        return result;
    }
}
