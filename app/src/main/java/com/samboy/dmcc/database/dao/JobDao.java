package com.samboy.dmcc.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.samboy.dmcc.jobs.model.Job;

@Dao
public interface JobDao {

    @Query("SELECT * FROM jobs WHERE userId = :id")
    Job getJob(String id);

    @Insert
    void addJob(Job job);

    @Delete
    void logout(Job job);

    @Update
    void updateJob(Job job);

    @Query("DELETE  FROM jobs")
    void deleteAll();
}
