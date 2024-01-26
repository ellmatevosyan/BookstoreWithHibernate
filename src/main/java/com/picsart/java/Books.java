package com.picsart.java;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Books {

    /*
    * CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    author VARCHAR(40) NOT NULL,
    genre VARCHAR(30) NOT NULL,
    price REAL NOT NULL CHECK(price > 0),
    quantity_in_stock INTEGER NOT NULL CHECK(quantity_in_stock >= 0)
);*/

    @Id
    @Column(name = "book_id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "author", nullable = false, length = 40)
    private String author;
    @Column(name = "genre", nullable = false,length = 30)
    private String genre;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantity_in_stock;

    public Books() {
    }

    public Books(String title, String author, String genre, Double price, Integer quantity_in_stock) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantity_in_stock = quantity_in_stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(Integer quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    @Override
    public String toString() {
        return "Books{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", quantity_in_stock=" + quantity_in_stock +
                '}';
    }
}
