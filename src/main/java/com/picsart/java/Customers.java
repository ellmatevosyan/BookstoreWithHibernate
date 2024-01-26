package com.picsart.java;

import jakarta.persistence.*;

/*
* CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(60) UNIQUE,
    phone VARCHAR(20) NOT NULL
);*/

@Entity
@Table(name = "customers")
public class Customers {

    @Id
    @Column(name = "customer_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    @Column(name = "name",nullable = false,length = 20)
    private String name;

    @Column(name = "email",unique = true,length = 60)
    private String email;

    @Column(name = "phone", nullable = false,length = 20)
    private String phone;

    public Customers(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customers() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
