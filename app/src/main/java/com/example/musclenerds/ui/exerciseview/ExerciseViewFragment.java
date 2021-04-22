package com.example.musclenerds.ui.exerciseview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.Muscle;
import com.example.musclenerds.model.MuscleGroup;
import com.example.musclenerds.model.MuscleGroups;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.exerciseview.Adapters.MuscleGroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewFragment extends Fragment {

    private AppDatabase mDb; //create the database var.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext()); // tell the database to create itself.

        View view = inflater.inflate(R.layout.exercise_view_fragment, container, false);

        ArrayList<MuscleExercises> muscleExercises = prepareData();

        MuscleGroupAdapter muscleGroupAdapter = new MuscleGroupAdapter(muscleExercises, view.getContext());

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        RecyclerView rvMuscleGroup = view.findViewById(R.id.rvSubject);

        rvMuscleGroup.setHasFixedSize(true);
        rvMuscleGroup.setLayoutManager(manager);
        rvMuscleGroup.setAdapter(muscleGroupAdapter);

        //Add buttons to they recyclerview

        return view;
    }

    //Creates the data to be displayed in the recyclerview, a list of muscles with a list of exercises.

    private ArrayList<MuscleExercises> prepareData() {

        ArrayList<MuscleExercises> muscles = new ArrayList<>();

        // create a background thread.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of workouts.
                // Use the type of Workout defined in the database though.

                //Muscle group with an array of workouts.
                List<Exercise> exercises = mDb.exerciseDAO().getAll();
                List<MuscleGroup> muscleGroups = mDb.muscleGroupDAO().getAll();

                ArrayList<MuscleGroup> uniqueMuscleGroups = new ArrayList<>();

                //Make a list of unique muscleGroups.
                for(int i = 0; i < muscleGroups.size(); i++){
                    MuscleGroup currentGroup = muscleGroups.get(i);

                    if(!uniqueMuscleGroups.contains(currentGroup)){
                        uniqueMuscleGroups.add(currentGroup);
                    }
                }

                //For each unique MuscleGroup, create a MuscleExercise with the name of the
                //MuscleGroup, and add each exercise with that MuscleGroupID to the MuscleExercise.
                for(int i = 0; i < muscleGroups.size(); i++) {
                    MuscleExercises muscleExercise = new MuscleExercises();
                    ArrayList<String> exerciseNames = new ArrayList<>();

                    muscleExercise.setName(muscleGroups.get(i).getName());


                    //Add the exercises to the musclegroup
                    for(int j = 0; j < exercises.size(); j++){

                        if(exercises.get(j).getMuscleGroupId() == muscleGroups.get(i).getId()){

                            if(!exerciseNames.contains(exercises.get(j).getName())){
                                muscleExercise.addExercise(exercises.get(j));
                                exerciseNames.add(exercises.get(j).getName());
                            }

                        }
                    }
                    //Add the MuscleExercises to an array to be set in the Adapters.
                    muscles.add(muscleExercise);
                }
            }
        });
        //List of MuscleGroups with their respective exercises.
        return muscles;
        }
    }