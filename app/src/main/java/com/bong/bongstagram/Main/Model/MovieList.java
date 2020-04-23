package com.bong.bongstagram.Main.Model;

public class MovieList {
    private String url;
    private String title;
    private String address;
    private String desc;
    private double latitude;
    private double longitude;


    public MovieList(String url, String title, String address, String desc, double latitude, double longitude){
        this.url = url;
        this.title = title;
        this.address = address;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
