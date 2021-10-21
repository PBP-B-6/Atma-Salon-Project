package com.example.atmasalon.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.atmasalon.entity.DataPelanggan;

import java.util.List;

@Dao
public interface DataPelangganDao {
    @Query("SELECT * FROM dataPelanggan WHERE userid = :userId")
    List<DataPelanggan> GetAll(int userId);

    @Insert
    void InsertDataPelanggan(DataPelanggan data);

    @Update
    void UpdateDataPelanggan(DataPelanggan data);

    @Delete
    void DeleteDataPelanggan(DataPelanggan data);
}
