package com.example.atmasalon.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.atmasalon.dao.DataPelangganDao;
import com.example.atmasalon.dao.UserDao;
import com.example.atmasalon.entity.DataPelanggan;
import com.example.atmasalon.entity.User;

@Database(entities = {User.class, DataPelanggan.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DataPelangganDao dataPelangganDao();
}
