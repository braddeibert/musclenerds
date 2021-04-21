package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "WORKOUT")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    int id;
    String Name;
    String Description;
    int Duration;
    int muscleGroup;

    @Ignore
    public Workout(String Name, String Description, int Duration, int muscleGroup){
        this.Name = Name;
        this.Description = Description;
        this.muscleGroup = muscleGroup;
        this.Duration = Duration;
    }
    public Workout(int id, String Name, String Description, int Duration, int muscleGroup){
        this.id = id;
        this.Name = Name;
        this.Description = Description;
        this.muscleGroup = muscleGroup;
        this.Duration = Duration;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return Name;}

    public void setName(String Name) {this.Name = Name;}

    public String getDescription() {return Description;}

    public void setDescription(String Description) {this.Description = Description;}

    public int getMuscleGroup() {return muscleGroup;}

    public void setMuscleGroup(int muscleGroup) {this.muscleGroup = muscleGroup;}

    public int getDuration() {return Duration;}

    public void setDuration() {this.Duration = Duration;}
}
