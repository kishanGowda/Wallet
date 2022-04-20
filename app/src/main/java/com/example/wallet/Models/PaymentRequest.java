package com.example.wallet.Models;

public class PaymentRequest {
    private String rupees, issuedDateTime, dueDate, feeType,orgID;
    int userId,id;
    String amount;
    String status;


    public PaymentRequest(String rupees, String issuedDateTime, String dueDate, String feeType, String orgID, int userId,int id,String amount,String status) {
        this.rupees = rupees;
        this.issuedDateTime = issuedDateTime;
        this.dueDate = dueDate;
        this.feeType = feeType;
        this.orgID = orgID;
        this.userId = userId;
        this.id=id;
        this.amount=amount;
        this.status=status;
    }

    public String getRupees() {
        return rupees;
    }

    public String getIssuedDateTime() {
        return issuedDateTime;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getOrgID() {
        return orgID;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}