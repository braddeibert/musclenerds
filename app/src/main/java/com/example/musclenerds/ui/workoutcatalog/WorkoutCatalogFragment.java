package com.example.musclenerds.ui.workoutcatalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.workoutcatalog.WorkoutAdapters.WorkoutMuscleGroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutCatalogFragment extends Fragment {

    private AppDatabase mDb; //create the database var.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext()); // tell the database to create itself.

        View view = inflater.inflate(R.layout.workout_catalog_fragment, container, false);

        ArrayList<WorkoutMuscleGroup> muscleGroups = prepareData();

        WorkoutMuscleGroupAdapter muscleGroupAdapter = new WorkoutMuscleGroupAdapter(muscleGroups, view.getContext());

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        RecyclerView rvMuscleGroup = view.findViewById(R.id.rvWorkoutMuscles);

        rvMuscleGroup.setHasFixedSize(true);
        rvMuscleGroup.setLayoutManager(manager);
        rvMuscleGroup.setAdapter(muscleGroupAdapter);

        //Add buttons to they recyclerview

        return view;
    }

    //Creates the data to be displayed in the recyclerview, a list of muscles with a list of exercises.

    private ArrayList<WorkoutMuscleGroup> prepareData() {

        ArrayList<WorkoutMuscleGroup> muscles = new ArrayList<WorkoutMuscleGroup>();

        // create a background thread.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of workouts.
                // Use the type of Workout defined in the database though.

                //Muscle group with an array of workouts.
                List<Workout> workouts = mDb.workoutDAO().getAll();

                //SELECT DISTINCT target_muscles FROM Workouts; would work in mySQL for one of each.
                ArrayList<String> muscleNames = new ArrayList<>();

                //Iterate through all workouts
                for (int i = 0; i < workouts.size(); i++) {

                    WorkoutMuscleGroup muscleGroup = new WorkoutMuscleGroup();

                    //Set name and ID to workout name and ID
                    muscleGroup.setId(workouts.get(i).getId());
                    muscleGroup.setName(workouts.get(i).getTarget_Muscles());

                    //Add all the workouts that have the same muscle group to the musclegroup list.
                    if(!muscleNames.contains(workouts.get(i).getTarget_Muscles())){
                        for (int j = 0; j < workouts.size(); j++) {
                            if (workouts.get(j).getTarget_Muscles().equals(muscleGroup.getName())) {
                                muscleGroup.addWorkout(workouts.get(j));
                            }
                        }

                        muscleNames.add(workouts.get(i).getTarget_Muscles());
                        muscles.add(muscleGroup);
                    }
                }
            }
        });
        return muscles;
    }
}