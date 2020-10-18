package com.samboy.dmcc.auth.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.samboy.dmcc.constants.SC;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    private int uuid;
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String image;


    public User(String id, String name, String email, String mobile, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
