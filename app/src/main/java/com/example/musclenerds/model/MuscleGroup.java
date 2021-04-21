package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MUSCLEGROUP")
public class MuscleGroup {
    @PrimaryKey(autoGenerate = true)
    int id;
    String Name;

    @Ignore
    public MuscleGroup(String Name) {
        this.Name = Name;
    }

    public MuscleGroup(int id, String Name) {
        this.id = id;
        this.Name = Name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
}
