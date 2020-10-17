package com.samboy.dmcc.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.database.dao.UserDao;

@androidx.room.Database(entities = {User.class},version = 1)
public abstract class Database extends RoomDatabase {

    private static String TAG = "DATABASE";
    public abstract UserDao userDao();
    private static Database instance;

    public static Database getInstance(Context context){
        if (instance == null){
            synchronized (Database.class){
                instance  = init(context);
            }
        }
        return instance;
    }


    private static Database init(Context context){
        return Room.databaseBuilder(context,Database.class,"dmcc_db")
                .addCallback(mCallback)
                .allowMainThreadQueries()
                .build();
    }

    private static Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "onCreate: " );

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.e(TAG, "onOpen: " );
        }

    };
}
