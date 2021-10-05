package com.example.atmasalon.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "riwayat")
public class Riwayat {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Riwayat(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
