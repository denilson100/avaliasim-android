package br.com.mobile10.avaliasim.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denmont on 18/04/2018.
 */

@IgnoreExtraProperties
public class Avaliacao2 implements Serializable, Comparable<Avaliacao2> {
    public String author;
    public String cidade;
    public String estado;
    public String title;
    public String type;
    public String idAvaliacao;
    public long timeStemp;
    public boolean visible;
    public List<String> features;
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

    // Apenas para mandar como privado
    public Avaliacao2(String idAvalicao) {
        this.idAvaliacao = idAvalicao;
    }

    private int getTotal(Avaliacao2 avaliacao) {
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
