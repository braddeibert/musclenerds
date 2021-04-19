package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.Muscle;

import java.util.List;

@Dao
public interface MuscleDAO {
    @Query("SELECT * FROM MUSCLE ORDER BY id")
    List<Muscle> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Muscle muscle);

    @Update
    void update(Muscle muscle);

    @Delete
    void delete(Muscle muscle);

    @Query("SELECT * FROM MUSCLE WHERE id = :id")
    Muscle findById(int id);
}
