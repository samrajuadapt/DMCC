package com.samboy.dmcc.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.samboy.dmcc.auth.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    User getLoggedInUser();


    @Insert
    void login(User user);

    @Update
    void update(User user);

    @Delete
    void logout(User user);
}
