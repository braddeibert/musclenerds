package com.example.musclenerds.ui.exerciseview;

import android.os.Bundle;
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

import java.util.List;

//Fragment class for the Individual exercise information. The exercise view screen with the description an GIF

public class IndividualExerciseFragment extends Fragment {

    public String exerciseName;
    public Exercise selectedExercise = new Exercise(1, "Okay", "Let's try", "Yup", 2, 3, "Two", "more");

    public IndividualExerciseFragment(String exerciseName){
        this.exerciseName = exerciseName;
    }

    private AppDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of exercises.
                List<Exercise> exercises = mDb.exerciseDAO().getAll();

                for(int i = 0; i < exercises.size(); i++){
                    if(exerciseName.equals(exercises.get(i).getName())){
                        selectedExercise = exercises.get(i);
                        break;
                    }
                }
            }
        });

        View view = inflater.inflate(R.layout.exercise_fragment, container, false);

        final TextView exerciseNameText = view.findViewById(R.id.textView7);
        final TextView exerciseTypeText = view.findViewById(R.id.textView9);
        final TextView muscleGroupsText = view.findViewById(R.id.textView8);
        final TextView equipmentText = view.findViewById(R.id.textView10);
        final TextView etcText = view.findViewById(R.id.textView11);
        final ImageView exerciseGif = view.findViewById(R.id.imageView2);

        exerciseNameText.setText(exerciseName);
        exerciseTypeText.setText(selectedExercise.getDescription());
        muscleGroupsText.setText(selectedExercise.getTarget_muscles());
        equipmentText.setText(selectedExercise.getEquipment());
        etcText.setText(String.valueOf(selectedExercise.getReps()));

        //Set the text to the database items depending on which imageview was clicked in ExerciseView Fragmment
        return view;
    }
}

