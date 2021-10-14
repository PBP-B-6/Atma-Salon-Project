package com.example.atmasalon.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.atmasalon.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> GetAllUser();

    @Query("SELECT * FROM user WHERE id = :id")
    User GetUser(int id);

    @Insert
    void InsertUser(User user);

    @Update
    void UpdateUser(User user);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    boolean CheckLogin(String email, String password);

    @Query("SELECT nama FROM user WHERE email = :email AND password = :password")
    String GetUserName(String email, String password);

    @Query("SELECT Id FROM user WHERE email = :email AND password = :password")
    int GetUserId(String email, String password);
}
