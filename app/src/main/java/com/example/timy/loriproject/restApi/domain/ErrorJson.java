package com.example.timy.loriproject.restApi.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorJson {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("details")
    @Expose
    private String details;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
