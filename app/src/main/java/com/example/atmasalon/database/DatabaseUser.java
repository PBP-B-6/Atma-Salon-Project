package com.example.atmasalon.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseUser {
    private Context context;
    private static DatabaseUser databaseUser;
    private AppDatabase database;

    public DatabaseUser(Context con)
    {
        context = con;
        database = Room.databaseBuilder(con, AppDatabase.class, "user").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseUser GetInstance(Context con)
    {
        if(databaseUser == null)
        {
            databaseUser = new DatabaseUser(con);
        }
        return databaseUser;
    }

    public AppDatabase GetDatabase(){return database;}
}
