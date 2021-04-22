package com.example.musclenerds;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;


import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.WorkoutExercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutTracking extends MainActivity {

    private Chronometer stopwatch;
    private Chronometer timer;
    private long pauseOffset;
    private boolean running;

    public AppBarConfiguration mAppBarConfiguration;
    private AppDatabase mDb; // make a reference to the database.

    Button weightDialog;
    Button repsDialog;
    TextView weightDisplay;
    TextView repsDisplay;
    int weightCount = 0;
    int repsCount = 0;
    TextView current_exercise_text;
    TextView up_next_exercise_text;
    int currentExercise = 1;
    private int workoutId;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_tracking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stopwatch = findViewById(R.id.stopwatch);
        timer = findViewById(R.id.timer);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation_view);

        weightDialog = findViewById(R.id.weightDialog);
        weightDisplay = findViewById(R.id.weightDisplay);

        repsDialog = findViewById(R.id.repsDialog);
        repsDisplay = findViewById(R.id.repsDisplay);

        weightDialog.setOnClickListener(view -> showWeightDialog());
        repsDialog.setOnClickListener(view -> showRepsDialog());

        current_exercise_text = findViewById(R.id.current_exercise_text);
        current_exercise_text.setOnClickListener(view -> showCurrentExercise());

        up_next_exercise_text = findViewById(R.id.up_next_exercise_text);
        up_next_exercise_text.setOnClickListener(view -> showUpNextExercise());

        timer.start();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.drawer_home, R.id.drawer_workouts, R.id.drawer_exercises, R.id.drawer_history)
                .setDrawerLayout(drawer)
                .build();

        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.drawer_home:
                    Intent intent1 = new Intent(WorkoutTracking.this, MainActivity.class);
                    startActivity(intent1);
                case R.id.drawer_exercises:
                    Intent intent2 = new Intent(WorkoutTracking.this, MainActivity.class);
                    startActivity(intent2);
                case R.id.drawer_workouts:
                    Intent intent3 = new Intent(WorkoutTracking.this, MainActivity.class);
                    startActivity(intent3);
                case R.id.drawer_history:
                    Intent intent4 = new Intent(WorkoutTracking.this, MainActivity.class);
                    startActivity(intent4);

                    break;
            }

            return false;
        });

        Bundle extras = getIntent().getExtras();

        // get workout to track if passed from workout catalog "start" button
        if (extras != null) {
            workoutId = extras.getInt("workoutId");
        }

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<WorkoutExercise> exercises = mDb.workoutExerciseDAO().findByW_ID(workoutId);
                Log.d("size_log", "" + exercises);

                List<Exercise> exercisesFromWorkout = new ArrayList<>();
                for (int i = 0; i < exercises.size(); i++) {
                    exercisesFromWorkout.add(mDb.exerciseDAO().findById(exercises.get(i).getE_ID()));
                }

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        String currentExerciseInfo = exercisesFromWorkout.get(0).getName() + ":\n" + "Sets: " + exercises.get(0).getSets() + "\nReps: " + exercises.get(0).getReps();
                        current_exercise_text.setText(currentExerciseInfo);
                        String upNextExerciseInfo = exercisesFromWorkout.get(1).getName() + ":\n" + "Sets: " + exercises.get(1).getSets() + "\nReps: " + exercises.get(1).getReps();
                        up_next_exercise_text.setText(upNextExerciseInfo);
                    }
                });

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("running", running);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        running = prefs.getBoolean("running", false);
    }

    public void startStopwatch(View v) {
        if(!running){
            stopwatch.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            stopwatch.start();
            running = true;
        }
    }

    public void pauseStopwatch(View v) {
        if(running) {
            stopwatch.stop();
            pauseOffset = SystemClock.elapsedRealtime() - stopwatch.getBase();
            running = false;
        }
    }

    public void resetStopwatch(View v) {
        stopwatch.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    //function to display weight dialog
    void showWeightDialog() {
        final Dialog dialog = new Dialog(WorkoutTracking.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.enter_weight);

        final EditText weightEnter = dialog.findViewById(R.id.editWeight);
        Button doneButton = dialog.findViewById(R.id.done_button);

        //resets weight count to the value of weight entered upon click of text box
        weightEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    weightCount = Integer.parseInt(s.toString());
                    System.out.println(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        doneButton.setOnClickListener((v) -> {
            String weightEntered = weightEnter.getText().toString();
            populateWeightSet(weightEntered);
            dialog.dismiss();
        });

        Button w_45 = dialog.findViewById(R.id.w_45);
        w_45.setOnClickListener((v) -> {
            weightCount = weightCount + 45;
            weightEnter.setText(String.valueOf(weightCount));
        });

        Button w_35 = dialog.findViewById(R.id.w_35);
        w_35.setOnClickListener((v) -> {
            weightCount = weightCount + 35;
            weightEnter.setText(String.valueOf(weightCount));
        });

        Button w_25 = dialog.findViewById(R.id.w_25);
        w_25.setOnClickListener((v) -> {
            weightCount = weightCount + 25;
            weightEnter.setText(String.valueOf(weightCount));
        });

        Button w_10 = dialog.findViewById(R.id.w_10);
        w_10.setOnClickListener((v) -> {
            weightCount = weightCount + 10;
            weightEnter.setText(String.valueOf(weightCount));
        });

        Button w_5 = dialog.findViewById(R.id.w_5);
        w_5.setOnClickListener((v) -> {
            weightCount = weightCount + 5;
            weightEnter.setText(String.valueOf(weightCount));
        });

        dialog.show();
    }

    void populateWeightSet(String weightEntered) {
        weightDisplay.setVisibility(View.VISIBLE);
        weightDisplay.setText(String.format(getString(R.string.weight_info), weightEntered));
    }

    //function to display reps dialog
    void showRepsDialog() {
        final Dialog dialog = new Dialog(WorkoutTracking.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.enter_reps);

        final EditText repsEnter = dialog.findViewById(R.id.editReps);
        Button doneButton = dialog.findViewById(R.id.reps_done_button);

        //resets reps count to the value of reps entered upon click of text box
        repsEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    repsCount = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        doneButton.setOnClickListener((v) -> {
            String repsEntered = repsEnter.getText().toString();
            populateRepsSet(repsEntered);
            dialog.dismiss();
        });

        Button r_1 = dialog.findViewById(R.id.r_1);
        r_1.setOnClickListener((v) -> {
            repsCount = repsCount + 1;
            repsEnter.setText(String.valueOf(repsCount));
        });

        Button r_5 = dialog.findViewById(R.id.r_5);
        r_5.setOnClickListener((v) -> {
            repsCount = repsCount + 5;
            repsEnter.setText(String.valueOf(repsCount));
        });

        Button r_10 = dialog.findViewById(R.id.r_10);
        r_10.setOnClickListener((v) -> {
            repsCount = repsCount + 10;
            repsEnter.setText(String.valueOf(repsCount));
        });

        Button r_20 = dialog.findViewById(R.id.r_20);
        r_20.setOnClickListener((v) -> {
            repsCount = repsCount + 20;
            repsEnter.setText(String.valueOf(repsCount));
        });

        dialog.show();
    }

    void populateRepsSet(String repsEntered) {
        repsDisplay.setVisibility(View.VISIBLE);
        repsDisplay.setText(String.format(getString(R.string.reps_info), repsEntered));
    }

    void showCurrentExercise() {
        final Dialog currentDialog = new Dialog(WorkoutTracking.this);
        currentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        currentDialog.setCancelable(true);
        currentDialog.setContentView(R.layout.tracking_exercise_dialog);

        TextView currentName = currentDialog.findViewById(R.id.textView17);
        TextView currentDescription = currentDialog.findViewById(R.id.textView15);
        TextView currentMuscles = currentDialog.findViewById(R.id.textView16);

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Exercise exercise =  mDb.exerciseDAO().findById(currentExercise);
                //List<WorkoutExercise> workoutExercises = mDb.workoutExerciseDAO().findByW_ID(allExercises.get(1).getId());
                Log.d("size_log", "id: " + exercise.getName());
                final String cName = exercise.getName();
                final String cDesc = exercise.getDescription();
                //final String cMusc = exercise.getMuscleGroupId();

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        currentName.setText(cName);
                        currentDescription.setText(cDesc);


                    }
                });
            }
        });

        currentDialog.show();
    }

    void showUpNextExercise() {
        final Dialog upNextDialog = new Dialog(WorkoutTracking.this);
        upNextDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        upNextDialog.setCancelable(true);
        upNextDialog.setContentView(R.layout.tracking_exercise_dialog);

        TextView upNextName = upNextDialog.findViewById(R.id.textView17);
        TextView upNextDescription = upNextDialog.findViewById(R.id.textView15);
        TextView upNextMuscles = upNextDialog.findViewById(R.id.textView16);

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Exercise exercise =  mDb.exerciseDAO().findById(currentExercise + 1);
                Log.d("size_log", "id: " + exercise);
                final String uNName = exercise.getName();
                final String uNDesc = exercise.getDescription();
                //final String cMusc = exercise.getMuscleGroupId();

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        upNextName.setText(uNName);
                        upNextDescription.setText(uNDesc);

                    }
                });
            }
        });

        upNextDialog.show();
    }
}

