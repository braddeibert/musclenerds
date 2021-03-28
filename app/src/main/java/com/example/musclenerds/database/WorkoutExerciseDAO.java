package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.WorkoutExercise;

import java.util.List;

@Dao
public interface WorkoutExerciseDAO {
    @Query("SELECT * FROM WORKOUTEXERCISE ORDER BY ID")
    List<WorkoutExercise> getAll();

    @Insert
    void insert(WorkoutExercise exercise);

    @Update
    void update(WorkoutExercise exercise);

    @Delete
    void delete(WorkoutExercise exercise);

    @Query("SELECT * FROM TRACKEDSET WHERE id = :id")
    WorkoutExercise findById(int id);
}