package com.example.wallet.Api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class PincodeResponse {
        @SerializedName("Message")
        public String message;
        @SerializedName("Status")
        public String status;
        @SerializedName("PostOffice")
        public ArrayList<PostOffice> postOffice;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<PostOffice> getPostOffice() {
        return postOffice;
    }
}



