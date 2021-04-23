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
import android.widget.SeekBar;
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

    SeekBar difficulty;
    Button weightDialog, repsDialog, trackSetButton, nextButton, prevButton;
    TextView weightDisplay, repsDisplay, setsDisplay, current_exercise_text, up_next_exercise_text;
    int weightCount = 0;
    int repsCount = 0;
    private int currentExerciseIndex = 0;
    private int currentSetIndex = 0;
    private int workoutId;

    // these lists contain the exercises in the workout currently being tracked, and the sets x reps for those exercises
    private List<Exercise> workoutExercises;
    private List<WorkoutExercise> linkedExercises;

    // these lists maintain references to the reps & weight for each set of each exercise, indexed by exercise.
    // i.e. setData[0] = list of data tracked for sets of 0th exercise
    // setData[0][0] = [ reps, weight, difficulty ]
    private List<List<String[]>> setData = new ArrayList<List<String[]>>();

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

        setsDisplay = findViewById(R.id.tvSets);
        trackSetButton = findViewById(R.id.trackSetButton);

        weightDialog.setOnClickListener(view -> showWeightDialog());
        repsDialog.setOnClickListener(view -> showRepsDialog());

        current_exercise_text = findViewById(R.id.current_exercise_text);
        current_exercise_text.setOnClickListener(view -> showCurrentExercise());

        up_next_exercise_text = findViewById(R.id.up_next_exercise_text);
        up_next_exercise_text.setOnClickListener(view -> showUpNextExercise());

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(view -> nextExercise());

        prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(view -> prevExercise());

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

        difficulty = findViewById(R.id.seekBar);
        difficulty.setProgress(0);
        difficulty.incrementProgressBy(1);
        difficulty.setMax(10);
        TextView difficultyValue = findViewById(R.id.difficultyValue);

        difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    if (progress >= 0 && progress <= 10) {

                        String progressString = String.valueOf(progress);
                        difficultyValue.setText(progressString);
                        difficulty.setSecondaryProgress(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        trackSetButton.setOnClickListener((v) -> {
            nextSet();
        });

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                linkedExercises = mDb.workoutExerciseDAO().findByW_ID(workoutId);
                Log.d("size_log", "" + linkedExercises);

                workoutExercises = new ArrayList<>();
                for (int i = 0; i < linkedExercises.size(); i++) {
                    workoutExercises.add(mDb.exerciseDAO().findById(linkedExercises.get(i).getE_ID()));
                }

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        initSetData();
                        updateExercise();
                        updateSet();
                    }
                });

            }
        });
    }

    private void initSetData() {
        for (int i = 0; i < workoutExercises.size(); i++) {
            String[] data = new String[3];
            setData.add(new ArrayList<String[]>());

            for (int j = 0; j < linkedExercises.get(i).getSets(); j++) {
                setData.get(i).add(data);
            }
        }
    }

    private void updateSet() {
        setData.get(currentExerciseIndex).set(currentSetIndex, new String[]
                { repsDisplay.getText().toString(), weightDisplay.getText().toString(), String.valueOf(difficulty.getProgress())}
        );

        setsDisplay.setText("Set: " + String.valueOf(currentSetIndex + 1));
    }

    private void nextSet() {
        if (currentSetIndex + 1 == linkedExercises.get(currentExerciseIndex).getSets() && currentExerciseIndex + 1 == workoutExercises.size()) {
            return;
        }

        if (currentSetIndex + 1 < linkedExercises.get(currentExerciseIndex).getSets()) {
            currentSetIndex++;
        }
        else {
            currentSetIndex = 0;

            nextExercise();
            updateExercise();
        }

        updateSet();
    }

    private void updateExercise() {
        String currentExerciseInfo = workoutExercises.get(currentExerciseIndex).getName() + ":\n" + "Sets: " + linkedExercises.get(currentExerciseIndex).getSets() + "\nReps: " + linkedExercises.get(currentExerciseIndex).getReps();
        current_exercise_text.setText(currentExerciseInfo);

        if (currentExerciseIndex + 1 < workoutExercises.size()) {
            String upNextExerciseInfo = workoutExercises.get(currentExerciseIndex + 1).getName() + ":\n" + "Sets: " + linkedExercises.get(currentExerciseIndex + 1).getSets() + "\nReps: " + linkedExercises.get(currentExerciseIndex + 1).getReps();
            up_next_exercise_text.setText(upNextExerciseInfo);
        }
        else {
            up_next_exercise_text.setText("Workout complete!");
        }
    }

    private void nextExercise() {
        if (currentExerciseIndex + 1 < workoutExercises.size()) {
            currentExerciseIndex++;
        }

        currentSetIndex = 0;
        updateExercise();
        updateSet();
    }

    private void prevExercise() {
        if (currentExerciseIndex > 0) {
            currentExerciseIndex--;
        }

        currentSetIndex = 0;
        updateExercise();
        updateSet();
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
        TextView currentType = currentDialog.findViewById(R.id.textView16);

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Exercise exercise =  workoutExercises.get(currentExerciseIndex);
                Log.d("size_log", "id: " + exercise.getName());
                final String cName = exercise.getName();
                final String cDesc = exercise.getDescription();
                final String cType = exercise.getType();

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        currentName.setText(cName);
                        currentDescription.setText(cDesc);
                        currentType.setText(cType);


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
        TextView upNextType = upNextDialog.findViewById(R.id.textView16);

        AppDatabase mDb = AppDatabase.getInstance(getBaseContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (currentExerciseIndex + 1 < workoutExercises.size()) {
                    Exercise exercise =  workoutExercises.get(currentExerciseIndex + 1);
                    Log.d("size_log", "id: " + exercise);
                    final String uNName = exercise.getName();
                    final String uNDesc = exercise.getDescription();
                    final String uNType = exercise.getType();

                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            upNextName.setText(uNName);
                            upNextDescription.setText(uNDesc);
                            upNextType.setText(uNType);

                        }
                    });
                }
            }
        });

        upNextDialog.show();
    }
}

