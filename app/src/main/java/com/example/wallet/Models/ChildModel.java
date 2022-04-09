package com.example.wallet.Models;

public class ChildModel {
    String type,date,amount;

    public ChildModel(String type, String date, String amount) {
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }
}
