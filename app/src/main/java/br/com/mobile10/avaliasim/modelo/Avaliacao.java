package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

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

    public String feature1;
    public String feature2;
    public String feature3;

    public List<String> features;

    public Avaliacao() {}

    // Produto
//    public Avaliacao(String idAvalicao, String title, String type, String author, boolean visible,
//                     String cidade, String estado, Feature feature11, Feature feature22, Feature feature33) {
//        this.idAvalicao = idAvalicao;
//        this.title = title;
//        this.type = type;
//        this.author = author;
//        this.visible = visible;
//
//        this.cidade = cidade;
//        this.estado = estado;
//    }

    public Avaliacao(String idAvalicao, String title, String type, String author, boolean visible,
                     String cidade, String estado, Long timeStamp, List<String> features) {
        this.idAvalicao = idAvalicao;
        this.title = title;
        this.type = type;
        this.author = author;
        this.visible = visible;
        this.cidade = cidade;
        this.estado = estado;
        this.timeStamp = timeStamp; // passa null pq pega do servidor
        this.features = features;
    }

//    public Avaliacao(String idAvalicao, String title, String type, String author, boolean visible,
//                     String cidade, String estado, String feature1, String feature2, String feature3) {
//        this.idAvalicao = idAvalicao;
//        this.title = title;
//        this.type = type;
//        this.author = author;
//        this.visible = visible;
//
//        this.cidade = cidade;
//        this.estado = estado;
//
//        this.feature1 = feature1;
//        this.feature2 = feature2;
//        this.feature3 = feature3;
//    }

    // Apenas para amndar como privado
    public Avaliacao(String idAvalicao) {
        this.idAvalicao = idAvalicao;
    }

    public Avaliacao(String title, String type) {
        this.title = title;
        this.type = type;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvalicao);
        result.put("title", title);
        result.put("type", type);
        result.put("author", author);
        result.put("visible", visible);
        result.put("cidade", cidade);
        result.put("estado", estado);
        result.put("timeStamp", ServerValue.TIMESTAMP);
//        result.put("feature1", feature1);
//        result.put("feature2", feature2);
//        result.put("feature3", feature3);

        return result;
    }

    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvalicao);

        return result;
    }
}
