package com.pahana.edu.model;

import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private int billId;
    private int accountNumber;
    private double totalAmount;
    private LocalDateTime billDate;
    private List<BillItem> items; // optional, when reading

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
}
