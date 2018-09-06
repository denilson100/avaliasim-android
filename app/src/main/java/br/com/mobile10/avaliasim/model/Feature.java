package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

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
}
