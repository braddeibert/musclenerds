package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TRACKEDWORKOUT")
public class TrackedWorkout {
    @PrimaryKey(autoGenerate = true)
    int id;
    int W_ID;
    int duration;
    String DateCompleted;

    @Ignore
    public TrackedWorkout(int W_ID, int duration, String DateCompleted) {
        this.W_ID = W_ID;
        this.duration = duration;
        this.DateCompleted = DateCompleted;
    }
    public TrackedWorkout(int id, int W_ID, int duration, String DateCompleted) {
        this.id = id;
        this.W_ID = W_ID;
        this.duration = duration;
        this.DateCompleted = DateCompleted;
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

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDateCompleted() {
        return this.DateCompleted;
    }
}
