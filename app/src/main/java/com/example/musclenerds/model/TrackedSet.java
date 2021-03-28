package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TRACKEDSET")
public class TrackedSet {
    @PrimaryKey(autoGenerate = true)
    int id;
    int E_ID; // what exercise this belongs to
    int T_ID; // what tracked workout this belongs to.
    int reps;
    int weight;
    int difficulty; // should this be a string or an int?
    String time;

    @Ignore
    public TrackedSet(int E_ID, int T_ID, int reps, int weight, int difficulty, String time) {
        this.E_ID = E_ID;
        this.T_ID = T_ID;
        this.reps = reps;
        this.weight = weight;
        this.difficulty = difficulty;
        this.time = time;
    }

    public TrackedSet(int id, int E_ID, int T_ID, int reps, int weight, int difficulty, String time) {
        this.id = id;
        this.E_ID = E_ID;
        this.T_ID = T_ID;
        this.reps = reps;
        this.weight = weight;
        this.difficulty = difficulty;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getE_ID() {
        return E_ID;
    }
    public void setE_ID(int E_ID) {
        this.E_ID = E_ID;
    }

    public int getT_ID() {
        return T_ID;
    }
    public void setT_ID(int T_ID) {
        this.T_ID = T_ID;
    }

    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
