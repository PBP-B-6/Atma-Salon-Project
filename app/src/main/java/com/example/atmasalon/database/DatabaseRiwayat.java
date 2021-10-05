package com.example.atmasalon.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseRiwayat {
    private Context context;
    private static DatabaseRiwayat databaseRiwayat;
    private AppDatabase database;

    public DatabaseRiwayat(Context con)
    {
        context = con;
        database = Room.databaseBuilder(con, AppDatabase.class, "riwayat").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseRiwayat GetInstance(Context con)
    {
        if(databaseRiwayat == null)
        {
            databaseRiwayat = new DatabaseRiwayat(con);
        }
        return databaseRiwayat;
    }

    public AppDatabase getDatabase(){return database;}
}
