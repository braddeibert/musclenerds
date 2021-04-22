package com.example.musclenerds.ui.workoutview;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.musclenerds.R;
import com.example.musclenerds.model.Workout;

import org.w3c.dom.Text;

public class WorkoutFragment extends Fragment {

    private Workout workout;

    public WorkoutFragment(Workout data) {
        workout = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_view, container, false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvWorkoutName);
        TextView tvDesc = (TextView) view.findViewById(R.id.tvDescription);
        TextView tvDuration = (TextView) view.findViewById(R.id.tvDuration);

        // This is the button that will trigger the tracking activity with the current workout
        Button trackWorkout = (Button) view.findViewById(R.id.buttonTrack);
        trackWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch tracking activity with the workout here
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        tvTitle.setText(workout.getName());
        tvDesc.append(workout.getDescription());
        tvDuration.append(Integer.toString(workout.getDuration()));

        return view;
    }
}