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

import android.view.View;
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

        weightDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeightDialog();
            }
        });

        repsDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRepsDialog();
            }
        });
    }

    //function to display weight dialog
    void showWeightDialog() {
        final Dialog dialog = new Dialog(WorkoutTracking.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.enter_weight);

        final EditText weightEnter = dialog.findViewById(R.id.editWeight);
        Button doneButton = dialog.findViewById(R.id.done_button);

        doneButton.setOnClickListener((v) -> {
            String weightEntered = weightEnter.getText().toString();
            populateWeightSet(weightEntered);
            dialog.dismiss();
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

        doneButton.setOnClickListener((v) -> {
            String repsEntered = repsEnter.getText().toString();
            populateRepsSet(repsEntered);
            dialog.dismiss();
        });

        dialog.show();
    }

    void populateRepsSet(String repsEntered) {
        repsDisplay.setVisibility(View.VISIBLE);
        repsDisplay.setText(String.format(getString(R.string.reps_info), repsEntered));
    }
}
