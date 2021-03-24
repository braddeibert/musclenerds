package com.example.musclenerds.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.musclenerds.model.MotivationalQuote;

//The database represents the whole database.
//It uses DAO object to interact with database tables.
//Will add additional Dao's at a later date.
//See below for brief explanation on the singleton pattern.

//this tells ROOm that this is a database, what entities it has, etc.
@Database(entities = {MotivationalQuote.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = "AppData_LOG"; // name used in debug log.
    private static final Object LOCK = new Object(); // not sure tbh.
    private static final String DATABASE_NAME = "musclenerds_DB"; // database name.
    private static AppDatabase sInstance;

    //Databases are expensive so we don't want to create multiple instances of them.
    //this ensures only one instance is created.
    //always use mDb = AppDatabase.getInstance(getApplicationContext()); to create a context of this.
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, String.valueOf(sInstance));
            }
        }
        if(sInstance == null) {
            Log.d(LOG_TAG, "unable to create database");
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MotivationalQuoteDAO quoteDao(); // the dao is define abstractly.
}