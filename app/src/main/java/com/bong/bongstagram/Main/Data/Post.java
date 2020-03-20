package com.bong.bongstagram.Main.Data;

import java.math.BigDecimal;

public class Post {
    private String access_token;
    private BigDecimal user_id;

    public Post(String access_token, BigDecimal user_id){
        this.access_token = access_token;
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public BigDecimal getUser_id() {
        return user_id;
    }
}
