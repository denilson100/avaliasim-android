package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.List;

public class Rating implements Serializable {
    private String id;
    private String deliverableId;
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

    @PropertyName("deliverable")
    public String getDeliverableId() {
        return deliverableId;
    }


    @PropertyName("deliverable")
    public void setDeliverableId(String deliverableId) {
        this.deliverableId = deliverableId;
    }
}