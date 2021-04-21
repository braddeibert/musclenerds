package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "EXERCISE")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    int id;
    int muscleGroupId;
    String name;
    String description;
    String type;
    String video_link;

    @Ignore
    public Exercise(int muscleGroupId, String name, String description, String type, String video_link) {
        this.muscleGroupId = muscleGroupId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.video_link = video_link;
    }

    public Exercise(int id, int muscleGroupId, String name, String description, String type, String video_link) {
        this.id = id;
        this.muscleGroupId = muscleGroupId;
        this.name = name;
        this.description = description;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String link) {
        this.video_link = link;
    }

    public void setMuscleGroupId (int id) {
        this.muscleGroupId = id;
    }
    public int getMuscleGroupId() {
        return muscleGroupId;
    }
}
