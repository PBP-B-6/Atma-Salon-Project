package com.example.atmasalon.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseDataPelanggan {
    private Context context;
    private static DatabaseDataPelanggan databaseDataPelanggan;
    private AppDatabase database;

    public DatabaseDataPelanggan(Context con)
    {
        context = con;
        database = Room.databaseBuilder(con, AppDatabase.class, "dataPelanggan").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseDataPelanggan GetInstance(Context con)
    {
        if(databaseDataPelanggan == null)
        {
            databaseDataPelanggan = new DatabaseDataPelanggan(con);
        }
        return databaseDataPelanggan;
    }

    public AppDatabase getDatabase(){return database;}
}
