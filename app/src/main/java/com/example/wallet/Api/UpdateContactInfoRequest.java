package com.example.wallet.Api;

public class UpdateContactInfoRequest {

        public String newEmail;

    public UpdateContactInfoRequest(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }
}
