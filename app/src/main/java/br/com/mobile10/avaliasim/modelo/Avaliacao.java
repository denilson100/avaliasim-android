package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denmont on 16/04/2018.
 */

@IgnoreExtraProperties
public class Avaliacao implements Serializable {
    public String idAvalicao;
    public String title;
    public String type;
    public boolean visible;
    public String author;
    public String cidade;
    public String estado;
    public Long timeStamp;

    public List<String> features;

    public Avaliacao() {}

    // Apenas para mandar como privado
    public Avaliacao(String idAvalicao) {
        this.idAvalicao = idAvalicao;
    }

    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvalicao);

        return result;
    }
}