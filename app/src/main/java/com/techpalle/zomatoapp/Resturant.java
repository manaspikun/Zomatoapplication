package com.techpalle.zomatoapp;

import org.json.JSONObject;

/**
 * Created by manasranjan on 1/26/2017.
 */

public class Resturant {
    private String name, locality,address,imageUrl,latitude,longitude;

    public Resturant(String name, String address, String locality, String imageUrl, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.locality = locality;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
