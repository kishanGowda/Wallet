package com.example.wallet.Models;

public class ChildModel {
    String type,date,amount,invoice;
    String issuedDate,paymentOption,receipt;


    public ChildModel(String type, String date, String amount, String invoice, String issuedDate, String paymentOption, String receipt) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.invoice = invoice;
        this.issuedDate = issuedDate;
        this.paymentOption = paymentOption;
        this.receipt = receipt;
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

    public String getInvoice() {
        return invoice;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public String getReceipt() {
        return receipt;
    }
}
