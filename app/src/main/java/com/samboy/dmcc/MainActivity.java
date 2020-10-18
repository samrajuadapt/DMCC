package com.samboy.dmcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.samboy.dmcc.auth.ui.LoginActivity;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.database.dao.UserDao;
import com.samboy.dmcc.home.ui.HomeActivity;
import com.samboy.dmcc.profile.ui.ProfileActivity;

public class MainActivity extends AppCompatActivity {
    private Database db;
    private UserDao userDao;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
        startTimer();
    }

    private void init(){
        db = Database.getInstance(this);
        userDao  = db.userDao();
    }

    private void startTimer(){
        new Handler().postDelayed(this::checkData,2000);
    }

    private void checkLogin(){
        if(userDao.getLoggedInUser()!=null){
            gotoHome();
        }else {
            gotoLogin();
        }
    }

    private void checkData(){
        if(db.jobDao().getJob(firebaseAuth.getCurrentUser().getUid())!=null){
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }else {
            gotoHome();
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