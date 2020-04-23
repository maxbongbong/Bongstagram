package com.bong.bongstagram.Main.Model;

public class ImageList {
    private String url;
    private String url2;
    private String url3;

    public ImageList(String url, String url2, String url3){
        this.url = url;
        this.url2 = url2;
        this.url3 = url3;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
