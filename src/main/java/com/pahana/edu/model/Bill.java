package com.pahana.edu.model;

import java.time.LocalDateTime;

public class Bill {
    private int billId;
    private int accountNumber;
    private int unitsConsumed;
    private double unitRate;
    private double totalAmount;
    private LocalDateTime billDate;

    // Getters & Setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    public int getUnitsConsumed() { return unitsConsumed; }
    public void setUnitsConsumed(int unitsConsumed) { this.unitsConsumed = unitsConsumed; }

    public double getUnitRate() { return unitRate; }
    public void setUnitRate(double unitRate) { this.unitRate = unitRate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
}
