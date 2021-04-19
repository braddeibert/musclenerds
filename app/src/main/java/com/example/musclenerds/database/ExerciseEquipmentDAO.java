package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.ExerciseEquipment;

import java.util.List;

@Dao
public interface ExerciseEquipmentDAO {
    @Query("SELECT * FROM EXERCISEEQUIPMENT ORDER BY ID")
    List<ExerciseEquipment> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExerciseEquipment exercise);

    @Update
    void update(ExerciseEquipment exercise);

    @Delete
    void delete(ExerciseEquipment exercise);

    @Query("SELECT * FROM EXERCISEEQUIPMENT WHERE id = :id")
    ExerciseEquipment findById(int id);

    @Query("SELECT * FROM EXERCISEEQUIPMENT WHERE E_ID = :E_ID")
    List<ExerciseEquipment> findByE_ID(int E_ID);
}
