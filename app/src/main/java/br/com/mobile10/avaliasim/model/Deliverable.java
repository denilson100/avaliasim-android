package br.com.mobile10.avaliasim.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

//Representa um produto ou serviço, que pode ser entregue por uma empresa a um cliente
@IgnoreExtraProperties
public class Deliverable implements Serializable {
    private String name;
    private String company;
    private String type;
    private Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}