package com.example.musclenerds.ui.workoutcatalog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.Workout;

//Model for the musclegroup list. This is the scrollable part of the recyclerview

public class WorkoutMuscleExercises {
    public int id;
    public String muscleName;
    public ArrayList<Workout> workouts = new ArrayList<>();

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

    public ArrayList getWorkouts() {
        return this.workouts;
    }

    public void addWorkout(Workout workout) { workouts.add(workout); }
}


