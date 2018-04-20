package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class Categories {
    private Integer categoryid;
    private String name;

    public Categories() {
    }

    public Categories(Integer categoryid, String name) {
        this.categoryid = categoryid;
        this.name = name;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
