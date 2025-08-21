package com.pahana.edu.model;

import java.math.BigDecimal;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    public Item() {}

    public Item(int itemId, String name, String description, BigDecimal price, int stock) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, String description, BigDecimal price, int stock) {
        this(0, name, description, price, stock);
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
