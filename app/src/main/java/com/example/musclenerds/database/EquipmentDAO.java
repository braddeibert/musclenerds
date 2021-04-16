package com.example.musclenerds.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musclenerds.model.Equipment;

import java.util.List;

@Dao
public interface EquipmentDAO {
    @Query("SELECT * FROM EQUIPMENT ORDER BY id")
    List<Equipment> getAll();

    @Insert
    void insert(Equipment equipment);

    @Update
    void update(Equipment equipment);

    @Delete
    void delete(Equipment equipment);

    @Query("SELECT * FROM EQUIPMENT WHERE id = :id")
    Equipment findById(int id);
}
