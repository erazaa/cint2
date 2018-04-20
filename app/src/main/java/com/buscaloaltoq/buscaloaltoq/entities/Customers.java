package com.buscaloaltoq.buscaloaltoq.entities;

/**
 * Created by rz on 15/04/2018.
 */

public class Customers {

    private  Integer customerid;
    private  String name;
    private  String lastname;
    private  Integer dni;
    private  String address;
    private  Integer phone;
    private  String email;
    private  String password;

    public Customers() {
    }

    public Customers(Integer customerid, String name, String lastname, Integer dni, String address, Integer phone, String email, String password) {
        this.customerid = customerid;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
