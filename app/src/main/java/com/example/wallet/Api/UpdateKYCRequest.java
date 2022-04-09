package com.example.wallet.Api;

public class UpdateKYCRequest {

        public String name;
        public String relation;
        public String phone;
        public String gender;
        public String dob;
        public String email;
        public String address;
        public String panNo;
        public String panFront;
        public String addressType;
        public String documentNo;
        public String documentFront;
        public String documentBack;

    public UpdateKYCRequest(String name, String relation, String phone, String gender, String dob, String email, String address, String panNo, String panFront, String addressType, String documentNo, String documentFront, String documentBack) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.panNo = panNo;
        this.panFront = panFront;
        this.addressType = addressType;
        this.documentNo = documentNo;
        this.documentFront = documentFront;
        this.documentBack = documentBack;
    }
}
