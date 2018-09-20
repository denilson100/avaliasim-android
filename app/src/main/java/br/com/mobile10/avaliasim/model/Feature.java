package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by denmont on 16/04/2018.
 */
@IgnoreExtraProperties
public class Feature implements Serializable {
    private String id;
    private String name;
    private Float stars;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
