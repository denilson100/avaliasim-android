package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denmont on 16/04/2018.
 */
@IgnoreExtraProperties
public class Rating implements Serializable {
    private String id;
    private Deliverable deliverable;
    private String title;
    private Long timestamp;
    private List<Feature> features;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Deliverable getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }
}