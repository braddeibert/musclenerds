package com.example.musclenerds.ui.exerciseview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.MuscleGroup;

import java.util.List;

//Fragment class for the Individual exercise information. The exercise view screen with the description an GIF

public class IndividualExerciseFragment extends Fragment {

    public String exerciseName;
    public Exercise selectedExercise = new Exercise(1, "String", "String", "String", "String");
    public String muscleGroupName;

    public IndividualExerciseFragment(String exerciseName){
        this.exerciseName = exerciseName;
    }

    private AppDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext());

        View view = inflater.inflate(R.layout.exercise_fragment, container, false);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of exercises.
                List<Exercise> exercises = mDb.exerciseDAO().getAll();
                List<MuscleGroup> muscleGroups = mDb.muscleGroupDAO().getAll();

                for(int i = 0; i < exercises.size(); i++) {
                    if (exerciseName.equals(exercises.get(i).getName())) {
                        selectedExercise = exercises.get(i);

                        //Pick out which muscleGroup the exercise is in
                        for(int j = 0; j < muscleGroups.size(); j++){
                            if(muscleGroups.get(j).getId() == selectedExercise.getMuscleGroupId()){
                                muscleGroupName = muscleGroups.get(j).getName();
                            }
                        }
                        break;
                    }
                }

                //Set the text to the database items depending on which imageview was clicked in ExerciseView Fragmment
                final TextView exerciseNameText = view.findViewById(R.id.textView7);
                final TextView typeText = view.findViewById(R.id.textView6);
                final TextView muscleGroupsText = view.findViewById(R.id.textView2);
                final TextView etcText = view.findViewById(R.id.textView11);


                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        exerciseNameText.setText(exerciseName);
                        muscleGroupsText.append(muscleGroupName);
                        etcText.append(String.valueOf(selectedExercise.getDescription()));
                        typeText.append(String.valueOf(selectedExercise.getType()));
                    }
                });
            }
        });
        return view;
    }
}

