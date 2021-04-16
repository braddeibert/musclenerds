package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.MuscleGroup;

import java.util.List;

@Dao
public interface MuscleGroupDAO {
    @Query("SELECT * FROM MUSCLEGROUP ORDER BY id")
    List<MuscleGroup> getAll();

    @Insert
    void insert(MuscleGroup muscleGroup);

    @Update
    void update(MuscleGroup muscleGroup);

    @Delete
    void delete(MuscleGroup muscleGroup);

    @Query("SELECT * FROM MUSCLEGROUP WHERE id = :id")
    MuscleGroup findById(int id);
}
