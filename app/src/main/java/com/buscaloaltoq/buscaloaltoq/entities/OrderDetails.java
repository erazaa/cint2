package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class OrderDetails {

    private String name;
    private Integer quantity;
    private Double subtotal;
    private String photo;
    private String description;

    public OrderDetails() {
    }

    public OrderDetails(String name, Integer quantity, Double subtotal, String photo, String description) {
        this.name = name;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.photo = photo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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
