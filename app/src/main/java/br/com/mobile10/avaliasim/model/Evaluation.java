package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluation implements Serializable {
    private String id;
    private String authorId;
    private String nameAuthor;
    private String photoAuthor;
    private List<String> features;
    private String[] featureArray;

    public Evaluation(){}

    public Evaluation(String id, String authorId, String photoAuthor, String nameAuthor, List<String> features) {
        this.id = id;
        this.authorId = authorId;
        this.nameAuthor = nameAuthor;
        this.photoAuthor = photoAuthor;
        this.features = features;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getPhotoAuthor() {
        return photoAuthor;
    }

    public void setPhotoAuthor(String photoAuthor) {
        this.photoAuthor = photoAuthor;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String[] getFeatureArray() {
        return featureArray;
    }

    public void setFeatureArray(String[] featureArray) {
        this.featureArray = featureArray;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("authorName", nameAuthor);
        result.put("authorPhoto", photoAuthor);
        result.put("features", features);

        return result;
    }
}
