package com.example.wallet.Api;

public class VerifyRequest {
    public String phone;
    public String otp;

    public VerifyRequest(String phone, String otp) {
        this.phone = phone;
        this.otp = otp;
    }
}
