package com.sportstore.entity;

import java.io.Serializable;

public class Shoes implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String shoesname;
    private String price;
    private int quantity;
    private boolean isvip;
    private String description;
    
    public Shoes() {}
    
    public Shoes(Shoes shoes) {
    	this.id = shoes.id;
    	this.shoesname = shoes.shoesname;
    	this.price = shoes.price;
    	this.quantity = shoes.quantity;
    	this.isvip = shoes.isvip;
    	this.description = shoes.description;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShoesname() {
        return shoesname;
    }

    public void setShoesname(String shoesname) {
        this.shoesname = shoesname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getIsvip() {
        return isvip;
    }

    public void setIsvip(boolean isvip) {
        this.isvip = isvip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
