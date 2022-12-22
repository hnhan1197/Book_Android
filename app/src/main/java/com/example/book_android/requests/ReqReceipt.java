package com.example.book_android.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqReceipt {
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("book")
    @Expose
    private String book;

    public ReqReceipt(Integer price, String book) {
        this.price = price;
        this.book = book;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
