package com.example.timy.loriproject.restApi.domain;


import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
