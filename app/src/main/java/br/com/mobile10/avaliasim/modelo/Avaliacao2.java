package br.com.mobile10.avaliasim.modelo;

import android.support.annotation.NonNull;
import android.util.Log;

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
public class Avaliacao2 implements Serializable, Comparable<Avaliacao2> {
    public String author;
    public String cidade;
    public String estado;
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

    public int getTotal(Avaliacao2 avaliacao) {
        int positive = 0;
        int negative = 0;
        for (Feature feature : avaliacao.listaAvaliacoes) {
            for (MyDate date : feature.date) {
                positive += date.positive;
                negative += date.negative;
            }
        }
        return (positive + negative);
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

        return result;
    }

    @Exclude
    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idAvaliacao", idAvaliacao);

        return result;
    }

    @Override
    public int compareTo(@NonNull Avaliacao2 outraAvaliacao) {
        if (getTotal(this) > getTotal(outraAvaliacao)) {
            return -1;
        }
        if (getTotal(this) > getTotal(outraAvaliacao)) {
            return 1;
        }
        return 0;
    }
}
