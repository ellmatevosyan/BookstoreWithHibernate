package com.picsart.java;

import jakarta.persistence.*;

import java.time.LocalDate;

/*
* CREATE TABLE sales (
    sale_id SERIAL PRIMARY KEY,
    book_id INTEGER,
    customer_id INTEGER,
    date_of_sale DATE,
    quantity_sold INTEGER NOT NULL CHECK(quantity_sold >= 0),
    total_price REAL NOT NULL CHECK(total_price >= 0),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE SET NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE SET NULL
);*/
@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_id",nullable = false, unique = true)
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = true)
    private Books book;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = true)
    private Customers customer;

    @Column(name = "date_of_sale")
    private LocalDate dateOfSale;

    @Column(name = "quantity_sold", nullable = false)
    private Integer quantitySold;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public Sales() {
    }

    public Sales(Books book, Customers customer, LocalDate dateOfSale, Integer quantitySold, Double totalPrice) {
        this.book = book;
        this.customer = customer;
        this.dateOfSale = dateOfSale;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "The customer with the following id " + getCustomer() + " bought the book " +
                getBook() + " and paid " + getTotalPrice();
    }



}
