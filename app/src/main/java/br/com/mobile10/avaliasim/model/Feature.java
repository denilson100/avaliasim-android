package br.com.mobile10.avaliasim.model;

import java.io.Serializable;
import java.util.List;

public class Feature implements Serializable {
    public String name;
    public List<MyDate> date;

    public Feature() {}

    public Feature(String name, List<MyDate> date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MyDate> getDate() {
        return date;
    }

    public void setDate(List<MyDate> date) {
        this.date = date;
    }
}
