package br.com.mobile10.avaliasim.model;

import java.io.Serializable;

public class Feature implements Serializable {
    private String name;
    private Boolean good;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }
}
