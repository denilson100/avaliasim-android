package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denmont on 16/04/2018.
 */
@IgnoreExtraProperties
public class Produto extends Avaliacao {
    public String feature1;
    public String feature2;
    public String feature3;

    public Produto(String title, String type, String author, boolean visible,
                   String feature1, String feature2, String feature3) {
        this.title = title;
        this.type = type;
        this.author = author;
        this.visible = visible;
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.feature3 = feature3;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("type", type);
        result.put("author", author);
        result.put("visible", visible);
        result.put("feature1", feature1);
        result.put("feature2", feature2);
        result.put("feature3", feature3);

        return result;
    }

}
