package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MUSCLEGROUPS")
public class MuscleGroups {
    @PrimaryKey(autoGenerate = true)
    int id;
    int G_ID;
    int M_ID;

    @Ignore
    public MuscleGroups(int G_ID, int M_ID) {
        this.G_ID = G_ID;
        this.M_ID = M_ID;
    }
    public MuscleGroups(int id, int G_ID, int M_ID) {
        this.id = id;
        this.G_ID = G_ID;
        this.M_ID = M_ID;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getG_ID() {
        return G_ID;
    }
    public void setG_ID(int G_ID) {
        this.G_ID = G_ID;
    }

    public int getM_ID() {
        return M_ID;
    }
    public void setM_ID(int M_ID) {
        this.M_ID = M_ID;
    }
}
