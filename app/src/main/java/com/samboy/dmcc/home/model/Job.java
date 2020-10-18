package com.samboy.dmcc.home.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs")
public class Job {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String country;
    private String region;
    private String state;
    private String town;
    private String job;

    public Job() {
    }

    public Job(String userId, String country, String region, String state, String town, String job) {
        this.userId = userId;
        this.country = country;
        this.region = region;
        this.state = state;
        this.town = town;
        this.job = job;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

