package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

//Representa um produto ou serviço, que pode ser entregue por uma empresa a um cliente
@IgnoreExtraProperties
public class Deliverable implements Serializable {
    public String name;
    public String type;
    public Float price;

    public Deliverable() {
    }

    public Deliverable(String name, String type, Float price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getImageResource() {
        return type;
    }
}