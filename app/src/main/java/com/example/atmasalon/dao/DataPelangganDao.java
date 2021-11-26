package com.example.atmasalon.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.atmasalon.entity.Pelanggan;

import java.util.List;

@Dao
public interface DataPelangganDao {
    @Query("SELECT * FROM dataPelanggan WHERE userid = :userId")
    List<Pelanggan> GetAll(int userId);

    @Insert
    void InsertDataPelanggan(Pelanggan data);

    @Update
    void UpdateDataPelanggan(Pelanggan data);

    @Delete
    void DeleteDataPelanggan(Pelanggan data);
}
