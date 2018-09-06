package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by denmont on 16/04/2018.
 */

@IgnoreExtraProperties
public class Avaliacao implements Serializable {
    public String author;
    public String city;
    public String state;
    public String ratingId;
    public String title;
    public String type;
    public boolean isVisible;
    public Long timestamp;
//    public List<String> feature;


//    public Map<String, Object> toMap2() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("idAvaliacao", idAvalicao);
//
//        return result;
//    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}