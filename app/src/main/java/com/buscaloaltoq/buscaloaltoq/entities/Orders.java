package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class Orders {

    private Integer orderid;
    private Integer customerid;
    private Double total;
    private String created_at;


    public Orders() {
    }

    public Orders(Integer orderid, Integer customerid, Double total, String created_at) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.total = total;
        this.created_at = created_at;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
