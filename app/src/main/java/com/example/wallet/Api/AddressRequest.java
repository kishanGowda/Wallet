package com.example.wallet.Api;

public class AddressRequest {

    public String name;
    public String relation;
    public String phone;
    public String gender;
    public String dob;
    public String email;
    public String address;

    public AddressRequest(String name, String relation, String phone, String gender, String dob, String email, String address) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
