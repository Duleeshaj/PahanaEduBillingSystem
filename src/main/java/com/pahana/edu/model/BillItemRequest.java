package com.pahana.edu.model;

public class BillItemRequest {
    private int itemId;
    private int qty;

    // ✅ no-arg constructor
    public BillItemRequest() {}

    // existing all-args constructor
    public BillItemRequest(int itemId, int qty) {
        this.itemId = itemId;
        this.qty = qty;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
}
