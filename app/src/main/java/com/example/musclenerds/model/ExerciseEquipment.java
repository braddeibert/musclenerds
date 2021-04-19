package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "EXERCISEEQUIPMENT")
public class ExerciseEquipment {
    @PrimaryKey(autoGenerate = true)
    int id;
    int EQ_ID;
    int E_ID;

    @Ignore
    public ExerciseEquipment(int EQ_ID, int E_ID) {
        this.EQ_ID = EQ_ID;
        this.E_ID = E_ID;
    }
    public ExerciseEquipment(int id, int EQ_ID, int E_ID) {
        this.id = id;
        this.EQ_ID = EQ_ID;
        this.E_ID = E_ID;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEQ_ID() {
        return EQ_ID;
    }
    public void setEQ_ID(int EQ_ID) {
        this.EQ_ID = EQ_ID;
    }

    public int getE_ID() {
        return E_ID;
    }
    public void setE_ID(int E_ID) {
        this.E_ID = E_ID;
    }
}
