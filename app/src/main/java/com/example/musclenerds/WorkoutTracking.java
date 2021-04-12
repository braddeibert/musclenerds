package com.example.musclenerds;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musclenerds.database.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class WorkoutTracking extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private AppDatabase mDb; // make a reference to the database.

    Button weightDialog;
    Button repsDialog;
    TextView weightDisplay;
    TextView repsDisplay;
    int weightCount = 0;
    int repsCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_tracking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weightDialog = findViewById(R.id.weightDialog);
        weightDisplay = findViewById(R.id.weightDisplay);

        repsDialog = findViewById(R.id.repsDialog);
        repsDisplay = findViewById(R.id.repsDisplay);

        weightDialog.setOnClickListener(view -> showWeightDialog());
        repsDialog.setOnClickListener(view -> showRepsDialog());


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
            repsCount = repsCount + 5;
            repsEnter.setText(String.valueOf(repsCount));
        });

        dialog.show();
    }

    void populateRepsSet(String repsEntered) {
        repsDisplay.setVisibility(View.VISIBLE);
        repsDisplay.setText(String.format(getString(R.string.reps_info), repsEntered));
    }

}
