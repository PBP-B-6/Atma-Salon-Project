package com.example.atmasalon.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.atmasalon.dao.DataPelangganDao;
import com.example.atmasalon.dao.UserDao;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.entity.User;

@Database(entities = {User.class, Pelanggan.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DataPelangganDao dataPelangganDao();
}
