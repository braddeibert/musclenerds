package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.TrackedWorkout;

import java.util.List;

@Dao
public interface TrackedWorkoutDAO {
    @Query("SELECT * FROM TRACKEDWORKOUT ORDER BY ID")
    List<TrackedWorkout> getAllTrackedWorkouts();

    @Insert
    void insertTrackedWorkout(TrackedWorkout workout);

    @Update
    void updateTrackedWorkout(TrackedWorkout workout);

    @Delete
    void delete(TrackedWorkout workout);

    @Query("SELECT * FROM TRACKEDWORKOUT WHERE id = :id")
    TrackedWorkout findById(int id);
}
