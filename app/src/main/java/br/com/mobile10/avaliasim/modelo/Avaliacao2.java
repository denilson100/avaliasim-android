package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denmont on 18/04/2018.
 */

@IgnoreExtraProperties
public class Avaliacao2 implements Serializable {
    public String author;
    public String cidade;
    public String estado;
//    public String[] features;
    public List<String> features;
    public String idAvaliacao;
    public long timeStemp;
    public String title;
    public String type;
    public boolean visible;
    public List<Feature> listaAvaliacoes = new ArrayList<Feature>();

    public Avaliacao2(String author, String cidade, String estado, List<String> features,
                      String idAvaliacao, long timeStemp, String title, String type,
                      boolean visible, List<Feature> listaAvaliacoes) {
        this.author = author;
        this.cidade = cidade;
        this.estado = estado;
        this.features = features;
        this.idAvaliacao = idAvaliacao;
        this.timeStemp = timeStemp;
        this.title = title;
        this.type = type;
        this.visible = visible;
        this.listaAvaliacoes = listaAvaliacoes;
    }

    /**
     * Contrutor para enviar uma nova avaliação
     * @param author
     * @param cidade
     * @param estado
     * @param features
     * @param idAvaliacao
     * @param timeStemp
     * @param title
     * @param type
     * @param visible
     */
//    public Avaliacao2(String author, String cidade, String estado, String[] features,
//                      String idAvaliacao, long timeStemp, String title, String type,
//                      boolean visible) {
//        this.author = author;
//        this.cidade = cidade;
//        this.estado = estado;
//        this.features = features;
//        this.idAvaliacao = idAvaliacao;
//        this.timeStemp = timeStemp;
//        this.title = title;
//        this.type = type;
//        this.visible = visible;
//    }

    public Avaliacao2(String author, String cidade, String estado, List<String> features,
                      String idAvaliacao, long timeStemp, String title, String type,
                      boolean visible) {
        this.author = author;
        this.cidade = cidade;
        this.estado = estado;
        this.features = features;
        this.idAvaliacao = idAvaliacao;
        this.timeStemp = timeStemp;
        this.title = title;
        this.type = type;
        this.visible = visible;
    }

    // Apenas para amndar como privado
    public Avaliacao2(String idAvalicao) {
        this.idAvaliacao = idAvalicao;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvaliacao);
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

    @Exclude
    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvaliacao);

        return result;
    }
}
