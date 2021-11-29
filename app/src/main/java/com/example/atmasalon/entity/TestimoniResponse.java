package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestimoniResponse
{
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<TestimoniFromJson> testimoni;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TestimoniFromJson> getTestimoni() {
        return testimoni;
    }

    public void setTestimoni(List<TestimoniFromJson> testimoni) {
        this.testimoni = testimoni;
    }
}
