package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class Shops {

    private Integer customerid;
    private Integer productid;
    private Double subtotal;
    private Integer quantity;
    private Double price;
    private String name;
    private String photo;
    private String description;

    public Shops() {
    }

    public Shops(Integer customerid, Integer productid, Double subtotal, Integer quantity, Double price, String name, String photo, String description) {
        this.customerid = customerid;
        this.productid = productid;
        this.subtotal = subtotal;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.photo = photo;
        this.description = description;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
