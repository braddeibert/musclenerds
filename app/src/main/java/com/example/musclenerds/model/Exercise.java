package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "EXERCISE")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String description;
    String target_muscles;
    int sets;
    int reps;
    String equipment;
    String video_link;

    @Ignore
    public Exercise(String name, String description, String target_muscles, int reps, int sets, String equipment, String video_link) {
        this.name = name;
        this.description = description;
        this.target_muscles = target_muscles;
        this.sets = sets;
        this.reps = reps;
        this.equipment = equipment;
        this.video_link = video_link;
    }

    public Exercise(int id, String name, String description, String target_muscles, int reps, int sets, String equipment, String video_link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.target_muscles = target_muscles;
        this.sets = sets;
        this.reps = reps;
        this.equipment = equipment;
        this.video_link = video_link;
    }

    //the rest are standard getters and setters.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getTarget_muscles() {
        return target_muscles;
    }

    public void setTarget_muscles(String target_muscles) {
        this.target_muscles = target_muscles;
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String link) {
        this.video_link = link;
    }
}
