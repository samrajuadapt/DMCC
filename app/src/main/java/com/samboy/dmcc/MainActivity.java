package com.samboy.dmcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.samboy.dmcc.auth.ui.LoginActivity;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.database.dao.UserDao;
import com.samboy.dmcc.home.ui.HomeActivity;

public class MainActivity extends AppCompatActivity {
    private Database db;
    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startTimer();
    }

    private void init(){
        db = Database.getInstance(this);
        userDao  = db.userDao();
    }

    private void startTimer(){
        new Handler().postDelayed(this::checkLogin,2000);
    }

    private void checkLogin(){
        if(userDao.getLoggedInUser()!=null){
            gotoHome();
        }else {
            gotoLogin();
        }
    }

    private void gotoHome(){
        if(!isFinishing()){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
    private void gotoLogin(){
        if(!isFinishing()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}