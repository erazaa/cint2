package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class Products {

    private Integer productid;
    private Integer categoryid;
    private String name;
    private Double price;
    private String photo;
    private String description;
    private Integer stock;
    private String brand;
    private String created_at;


    public Products() {
    }

    public Products(Integer productid, Integer categoryid, String name, Double price, String photo, String description, Integer stock, String brand, String created_at) {
        this.productid = productid;
        this.categoryid = categoryid;
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.description = description;
        this.stock = stock;
        this.brand = brand;
        this.created_at = created_at;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
