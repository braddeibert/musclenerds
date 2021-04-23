package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.TrackedWorkout;

import java.util.List;

@Dao
public interface TrackedWorkoutDAO {
    @Query("SELECT * FROM TRACKEDWORKOUT ORDER BY ID")
    List<TrackedWorkout> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackedWorkout workout);

    @Update
    void update(TrackedWorkout workout);

    @Delete
    void delete(TrackedWorkout workout);

    @Query("SELECT * FROM TRACKEDWORKOUT WHERE id = :id")
    TrackedWorkout findById(int id);

    @Query("SELECT * FROM TRACKEDWORKOUT WHERE DateCompleted = :date ORDER BY DateCompleted DESC")
    List<TrackedWorkout> findByDate(String date);

    @Query("SELECT * FROM TRACKEDWORKOUT ORDER BY DateCompleted DESC")
    List <TrackedWorkout> getLatest();
}
