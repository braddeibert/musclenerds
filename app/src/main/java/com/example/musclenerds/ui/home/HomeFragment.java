package com.example.musclenerds.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.MotivationalQuote;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.model.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private AppDatabase mDb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDb = AppDatabase.getInstance(getContext());
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.greeting_label);
        final TextView quoteView = root.findViewById(R.id.textView5);
        final TextView wotd = root.findViewById(R.id.textView3);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });
        Random rand = new Random();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //first get all the quotes.
                List<MotivationalQuote> quotes = mDb.quoteDao().getAll();
                //get a random number. used to get a random quote from the above list.
                int index = rand.nextInt(quotes.size());
                //set the text of the desired item, quoteView is defined above.
                quoteView.setText(quotes.get(index).getText());

                //then we can get a random workout.
                //first get a list of all workouts.
                /*List<Workout> allWorkouts = mDb.workoutDAO().getAll();
                //get a new random number.
                index = rand.nextInt(allWorkouts.size() - 1);
                //create a list of workout id numbers by getting all workoutExercise items with the matching w_id from the randomly selected workout.
                List<WorkoutExercise> workoutExercises = mDb.workoutExerciseDAO().findByW_ID(allWorkouts.get(index).getId());

                //then make a list of all the exercises from the workoutExercises list.
                List<Exercise> exercises = new ArrayList<Exercise>();
                for(int i = 0; i < workoutExercises.size(); i++) {
                    exercises.add(mDb.exerciseDAO().findById(workoutExercises.get(i).getE_ID()));
                }

                String wotdText = allWorkouts.get(index).getName() + "\n" + allWorkouts.get(index).getDescription();

                for(int i = 0; i < exercises.size(); i++) {
                    wotdText += "\n- " + exercises.get(i).getReps() + " " + exercises.get(i).getName() + "s";
                }

                wotd.setText(wotdText);*/
            }
        });
        return root;
    }
}
