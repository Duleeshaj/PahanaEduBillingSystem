package com.pahana.edu.model;

public class BillItem {
    private int billItemId;
    private int billId;
    private int itemId;

    // NEW
    private String itemName;

    private int qty;
    private double unitPrice;

    // keep a stored line total; if your DAO doesn't set it, you can compute on the fly
    private double lineTotal;

    public int getBillItemId() { return billItemId; }
    public void setBillItemId(int billItemId) { this.billItemId = billItemId; }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    // NEW
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getLineTotal() {
        // if not set by DAO, compute
        return (lineTotal > 0) ? lineTotal : unitPrice * qty;
    }
    public void setLineTotal(double lineTotal) { this.lineTotal = lineTotal; }
}
