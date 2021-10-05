package com.example.atmasalon.database;

import android.content.Context;

import androidx.room.Room;

public class DatabasePelanggan {
    private Context context;
    private static DatabasePelanggan databaseUser;
    private AppDatabase database;

    public DatabasePelanggan(Context con)
    {
        context = con;
        database = Room.databaseBuilder(con, AppDatabase.class, "user").allowMainThreadQueries().build();
    }

    public static synchronized DatabasePelanggan GetInstance(Context con)
    {
        if(databaseUser == null)
        {
            databaseUser = new DatabasePelanggan(con);
        }
        return databaseUser;
    }

    public AppDatabase getDatabase(){return database;}
}
