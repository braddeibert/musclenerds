package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.TrackedSet;

import java.util.List;

@Dao
public interface TrackedSetDAO {
    @Query("SELECT * FROM TRACKEDSET ORDER BY ID")
    List<TrackedSet> getAll();

    @Insert
    void insert(TrackedSet set);

    @Update
    void update(TrackedSet set);

    @Delete
    void delete(TrackedSet set);

    @Query("SELECT * FROM TRACKEDSET WHERE id = :id")
    TrackedSet findById(int id);
}
