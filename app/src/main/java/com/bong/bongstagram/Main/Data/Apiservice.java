package com.bong.bongstagram.Main.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Apiservice {
    static final String API_URL = "https://api.instagram.com/oauth/";
    @FormUrlEncoded
    @POST("access_token")
    Call<Movie> postAccessToken(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("redirect_uri") String redirect_uri,
            @Field("code") String code
    );
}
