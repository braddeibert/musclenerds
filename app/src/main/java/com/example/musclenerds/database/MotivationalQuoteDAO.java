package com.example.musclenerds.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.MotivationalQuote;

import java.util.List;

//A Data Access Object is the interface we use to interact with a table in a database.
//This one interacts with the QUOTE database.
//the functions are abstract so we don't define them but we call them in other places.
//The tags above the function define how it behaves. notice how @Query tags must have a sql query defined.
//other tags can have things defined, EG: on conflict behavior.

@Dao
public interface MotivationalQuoteDAO {
    @Query("SELECT * FROM QUOTE ORDER BY ID")
    List<MotivationalQuote> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MotivationalQuote quote);

    @Update
    void update(MotivationalQuote quote);

    @Delete
    void delete(MotivationalQuote quote);

    @Query("SELECT * FROM QUOTE WHERE id = :id")
    MotivationalQuote findById(int id);
}
