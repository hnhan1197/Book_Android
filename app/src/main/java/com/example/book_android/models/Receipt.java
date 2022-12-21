package com.example.book_android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receipt {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("book")
    @Expose
    private Book book;

    public Receipt(Integer price, String bookID) {
        this.price = price;
        this.book.setId(bookID);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
