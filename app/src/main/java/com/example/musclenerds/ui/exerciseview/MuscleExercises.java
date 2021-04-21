package com.example.musclenerds.ui.exerciseview;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.musclenerds.model.Exercise;

//Model for the musclegroup list. This is the scrollable part of the recyclerview

public class MuscleExercises {
    public int id;
    public String muscleName;
    public ArrayList<Exercise> exercises = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return muscleName; }

    public void setName(String muscleName) {
        this.muscleName = muscleName;
    }

    public ArrayList getExercises() {
        return this.exercises;
    }

    public void addExercise(Exercise exercise) { exercises.add(exercise); }
}


