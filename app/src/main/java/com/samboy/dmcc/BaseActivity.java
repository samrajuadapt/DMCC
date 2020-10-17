package com.samboy.dmcc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static String TAG = "BASE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toast(String msg){
       toast(msg,Toast.LENGTH_SHORT);
    }

    public void toast(String msg,int duration){
        Toast.makeText(this, msg, duration).show();
    }
    public void log(String log){
        log(TAG,log);
    }
    public void log(String tag,String log){
        log(tag,"log",log);
    }
    public void log(String tag,String content,String log){
        Log.e(tag, content+": "+log );
    }
}
