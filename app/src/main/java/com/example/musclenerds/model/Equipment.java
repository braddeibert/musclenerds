package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "EQUIPMENT")
public class Equipment {
    @PrimaryKey(autoGenerate = true)
    int id;
    String Name;

    @Ignore
    public Equipment(String Name) {
        this.Name = Name;
    }

    public Equipment(int id, String Name) {
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
