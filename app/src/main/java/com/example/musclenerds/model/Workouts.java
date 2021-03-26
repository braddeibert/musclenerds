package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "WORKOUTS")
public class Workouts {

    @PrimaryKey(autoGenerate = true)
    int id;
    String Name;
    String Description;
    String Target_Muscles;
    int Duration;

    @Ignore
    public Workouts(String Name, String Description, String Target_Muscles, int Duration){
        this.Name = Name;
        this.Description = Description;
        this.Target_Muscles = Target_Muscles;
        this.Duration = Duration;
    }
    public Workouts(int id, String Name, String Description, String Target_Muscles, int Duration){
        this.id = id;
        this.Name = Name;
        this.Description = Description;
        this.Target_Muscles = Target_Muscles;
        this.Duration = Duration;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return Name;}

    public void setName(String Name) {this.Name = Name;}

    public String getDescription() {return Description;}

    public void setDescription(String Description) {this.Description = Description;}

    public String getTarget_Muscles() {return Target_Muscles;}

    public void setTarget_Muscles(String Target_Muscles) {this.Target_Muscles = Target_Muscles;}

    public int getDuration() {return Duration;}

    public void setDuration() {this.Duration = Duration;}
}
