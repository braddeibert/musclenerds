package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "WORKOUTEXERCISE")
public class WorkoutExercise {
    @PrimaryKey(autoGenerate = true)
    int id;
    int W_ID;
    int E_ID;
    int sets;
    int reps;

    @Ignore
    public WorkoutExercise(int W_ID, int E_ID, int sets, int reps) {
        this.W_ID = W_ID;
        this.E_ID = E_ID;
        this.sets = sets;
        this.reps = reps;
    }
    public WorkoutExercise(int id, int W_ID, int E_ID, int sets, int reps) {
        this.id = id;
        this.W_ID = W_ID;
        this.E_ID = E_ID;
        this.sets = sets;
        this.reps = reps;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getW_ID() {
        return W_ID;
    }
    public void setW_ID(int W_ID) {
        this.W_ID = W_ID;
    }

    public int getE_ID() {
        return E_ID;
    }
    public void setE_ID(int E_ID) {
        this.E_ID = E_ID;
    }

    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
}
