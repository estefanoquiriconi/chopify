package com.chopify.app.data.relationships;

import androidx.room.Embedded;

import com.chopify.app.data.entities.Business;

public class BusinessWithAddress {
    @Embedded
    private Business business;
    private String address;
    private double latitude;
    private double longitude;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}