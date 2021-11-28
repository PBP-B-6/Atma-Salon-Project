package com.example.atmasalon.entity;

import com.google.gson.annotations.SerializedName;

public class TestimoniResponse
{
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TestimoniFromJson testimoni;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TestimoniFromJson getTestimoni() {
        return testimoni;
    }

    public void setTestimoni(TestimoniFromJson testimoni) {
        this.testimoni = testimoni;
    }
}
