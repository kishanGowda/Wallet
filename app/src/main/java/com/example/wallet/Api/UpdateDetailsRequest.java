package com.example.wallet.Api;

public class UpdateDetailsRequest {
    public String name;
    public String relation;
    public String phone;
    public String gender;
    public String dob;
    public String email;

    public UpdateDetailsRequest(String name, String relation, String phone, String gender, String dob, String email) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
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
}

