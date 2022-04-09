package com.example.wallet.Api;

import com.google.gson.annotations.SerializedName;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.SerializedName; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class PostOffice {
    @SerializedName("Name")
    public String name;
    @SerializedName("Description")
    public Object description;
    @SerializedName("BranchType")
    public String branchType;
    @SerializedName("DeliveryStatus")
    public String deliveryStatus;
    @SerializedName("Circle")
    public String circle;
    @SerializedName("District")
    public String district;
    @SerializedName("Division")
    public String division;
    @SerializedName("Region")
    public String region;
    @SerializedName("Block")
    public String block;
    @SerializedName("State")
    public String state;
    @SerializedName("Country")
    public String country;
    @SerializedName("Pincode")
    public String pincode;

    public String getName() {
        return name;
    }

    public Object getDescription() {
        return description;
    }

    public String getBranchType() {
        return branchType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getCircle() {
        return circle;
    }

    public String getDistrict() {
        return district;
    }

    public String getDivision() {
        return division;
    }

    public String getRegion() {
        return region;
    }

    public String getBlock() {
        return block;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPincode() {
        return pincode;
    }
}
