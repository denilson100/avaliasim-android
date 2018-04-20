package br.com.mobile10.avaliasim.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denmont on 16/04/2018.
 */

@IgnoreExtraProperties
public class Feature implements Serializable {
    public String name;
    public List<MyDate> date;

    public Feature() {}

    public Feature(String name, List<MyDate> date) {
        this.name = name;
        this.date = date;
    }

    //    public Feature(String feature) {
//        this.feature = feature;
//    }
//
//    public Feature(String featire1, String featire2, String featire3) {
//        this.featire1 = featire1;
//        this.featire2 = featire2;
//        this.featire3 = featire3;
//    }
//
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("feature1", featire1);
//        result.put("feature2", featire2);
//        result.put("feature3", featire3);
//
//        return result;
//    }
}
