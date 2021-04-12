package com.example.musclenerds.ui.exerciseview;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.ui.exerciseview.Adapters.MuscleGroupAdapter;
import com.example.musclenerds.model.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewFragment extends Fragment {

    private AppDatabase mDb; //create the database var.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext()); // tell the database to create itself.

        View view = inflater.inflate(R.layout.exercise_view_fragment, container, false);

        ArrayList<MuscleGroup> muscleGroups = prepareData();

        MuscleGroupAdapter muscleGroupAdapter = new MuscleGroupAdapter(muscleGroups, view.getContext());

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        RecyclerView rvMuscleGroup = view.findViewById(R.id.rvSubject);

        rvMuscleGroup.setHasFixedSize(true);
        rvMuscleGroup.setLayoutManager(manager);
        rvMuscleGroup.setAdapter(muscleGroupAdapter);

        //Add buttons to they recyclerview

        return view;
    }

    //Creates the data to be displayed in the recyclerview, a list of muscles with a list of exercises.

    private ArrayList<MuscleGroup> prepareData() {

        ArrayList<MuscleGroup> muscles = new ArrayList<MuscleGroup>();

        // create a background thread.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of exercises.
                // Use the type of Exercise defined in the database though.

                //Muscle group with an array of exercises.
                List<Exercise> exercises = mDb.exerciseDAO().getAll();

                //List of muscle names so we can keep track of which ones have already been added
                //Kind of a hot fix, this could be solved quicker if we manipulated the
                //query calls a little bit, right now it's just working with a list of all the exercises, but we
                //could instead make a query call for one of each muscle_name, add those to a list, then for each
                //item in that list query all the exercises and add all the matching ones for each item. Maybe
                //I'll get on this right now, but I'm not really sure how to change up the query calls.

                //SELECT DISTINCT target_muscles FROM Exercises; would work in mySQL for one of each.
                ArrayList<String> muscleNames = new ArrayList<>();

                //Iterate through all exercises
                for (int i = 0; i < exercises.size(); i++) {

                    MuscleGroup muscleGroup = new MuscleGroup();

                    //Set name and ID to exercise name and ID
                    muscleGroup.setId(exercises.get(i).getId());
                    muscleGroup.setName(exercises.get(i).getTarget_muscles());

                    //Add all the exercises that have the same muscle group to the musclegroup list.
                    if(!muscleNames.contains(exercises.get(i).getTarget_muscles())){
                        for (int j = 0; j < exercises.size(); j++) {
                            if (exercises.get(j).getTarget_muscles().equals(muscleGroup.getName())) {
                                muscleGroup.addExercise(exercises.get(j));
                            }
                        }

                        muscleNames.add(exercises.get(i).getTarget_muscles());
                        muscles.add(muscleGroup);
                    }
                }
            }
        });
        return muscles;
        }
    }