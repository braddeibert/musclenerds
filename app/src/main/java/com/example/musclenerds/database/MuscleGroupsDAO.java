package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.MuscleGroups;

import java.util.List;

@Dao
public interface MuscleGroupsDAO {
    @Query("SELECT * FROM MUSCLEGROUPS ORDER BY ID")
    List<MuscleGroups> getAll();

    @Insert
    void insert(MuscleGroups group);

    @Update
    void update(MuscleGroups group);

    @Delete
    void delete(MuscleGroups group);

    @Query("SELECT * FROM MUSCLEGROUPS WHERE id = :id")
    MuscleGroups findById(int id);

    @Query("SELECT * FROM MUSCLEGROUPS WHERE G_ID = :G_ID")
    List<MuscleGroups> findByG_ID(int G_ID);
}
