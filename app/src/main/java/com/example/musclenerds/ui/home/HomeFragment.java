package com.example.musclenerds.ui.home;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.musclenerds.model.TrackedSet;
import com.example.musclenerds.model.TrackedWorkout;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.model.WorkoutExercise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Date;

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
        final TextView lastWorkoutHighlights = root.findViewById(R.id.textView4);
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
                //quoteView.setText(quotes.get(index).getText());

                final String fq = quotes.get(index).getText();
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        quoteView.setText(fq);
                    }
                });

                //then we can get a random workout.
                //first get a list of all workouts.
                List<Workout> allWorkouts = mDb.workoutDAO().getAll();
                //get a new random number.
                index = rand.nextInt(allWorkouts.size() - 1);
                //create a list of workout id numbers by getting all workoutExercise items with the matching w_id from the randomly selected workout.
                List<WorkoutExercise> workoutExercises = mDb.workoutExerciseDAO().findByW_ID(allWorkouts.get(index).getId());
                Log.d("size_log", "workout: \n" + allWorkouts.get(index).getName() + "\nid: " + allWorkouts.get(index).getId());

                //then make a list of all the exercises from the workoutExercises list.
                List<Exercise> exercises = new ArrayList<Exercise>();
                Log.d("size_log", "size: " + workoutExercises.size());
                for(int i = 0; i < workoutExercises.size(); i++) {
                    exercises.add(mDb.exerciseDAO().findById(workoutExercises.get(i).getE_ID()));
                }

                String wotdText = allWorkouts.get(index).getName() + "\n" + allWorkouts.get(index).getDescription();

                for(int i = 0; i < exercises.size(); i++) {
                    wotdText += "\n- " + exercises.get(i).getName();
                }

                final String fw = wotdText;


                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        wotd.setText(fw);
                    }
                });
            }
        });

        //Tracked workout thread below

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //create and insert test "TrackedWorkout" data
                Calendar testDate = Calendar.getInstance();
                TrackedWorkout testWorkoutData1 = new TrackedWorkout(1,3,60,String.valueOf(testDate.getTimeInMillis()-100000000));
                TrackedWorkout testWorkoutData2 = new TrackedWorkout(2,3,90,String.valueOf(testDate.getTimeInMillis()-1111111111));
                System.out.println(testWorkoutData1.getDuration()+ " | This is the test value for testWorkoutData1's duration");
                mDb.trackedWorkoutDAO().insert(testWorkoutData1);
                mDb.trackedWorkoutDAO().insert(testWorkoutData2);

                //get latest TrackedWorkout
                TrackedWorkout latestWorkout = mDb.trackedWorkoutDAO().getLatest().get(0);

                //create and insert test "TrackedSet" data for the above "TrackedWorkoutData1"
                TrackedSet testTrackedSet1 = new TrackedSet(1,3,1,5,60,5,String.valueOf(60));
                TrackedSet testTrackedSet2 = new TrackedSet(2,3,1,10,20,7,String.valueOf(60));
                mDb.trackedSetDAO().insert(testTrackedSet1);
                mDb.trackedSetDAO().insert(testTrackedSet2);

                //get list of TrackedSets from TrackedWorkout
                List<TrackedSet> latestWorkoutTrackedSetList = mDb.trackedSetDAO().findByT_ID(latestWorkout.getId());

                //create string for insertion onto LastWorkoutHighlights in HomeFragment
                String workoutName = mDb.workoutDAO().findById(latestWorkout.getId()).getName();
                String workoutDescription = mDb.workoutDAO().findById(latestWorkout.getId()).getDescription();
                Calendar conversionCalendar = Calendar.getInstance();
                conversionCalendar.setTimeInMillis(Long.parseLong((latestWorkout.getDateCompleted())));
                long workoutDate = Long.parseLong(latestWorkout.getDateCompleted());
                conversionCalendar.setTimeInMillis(workoutDate);

                String lwhText = "Workout: " + workoutName + "\n" + workoutDescription + "\n" + conversionCalendar.getTime(); //still needs date
                //System.out.println(lwhText);

                //get most difficult exercise
                TrackedSet mostDifficultTrackedSet = latestWorkoutTrackedSetList.get(0);
                for (int i=0; i < latestWorkoutTrackedSetList.size(); i++){
                    if(latestWorkoutTrackedSetList.get(i).getDifficulty()>mostDifficultTrackedSet.getDifficulty()){
                        mostDifficultTrackedSet = latestWorkoutTrackedSetList.get(i);
                    }
                }
                lwhText = lwhText +
                        "\n\n"+
                        "Exercise: " + mDb.exerciseDAO().findById(mostDifficultTrackedSet.getE_ID()).getName()+
                        "\nWeight: " + mostDifficultTrackedSet.getWeight()+
                        "\nReps: " + mostDifficultTrackedSet.getReps()+
                        "\nDifficulty: " + mostDifficultTrackedSet.getDifficulty();

                final String lwhFinalText = lwhText;

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        lastWorkoutHighlights.setText(lwhFinalText);
                    }
                });




            }
        });
        return root;

        //Tracked workout code below



    }
}