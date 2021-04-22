package com.example.musclenerds.ui.workoutview;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView tvTitle = (TextView) view.findViewById(R.id.textView6);
        TextView tvDesc = (TextView) view.findViewById(R.id.textView12);
        TextView tvDuration = (TextView) view.findViewById(R.id.textView13);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        tvTitle.setText(workout.getName());
        tvDesc.setText(workout.getDescription());
        tvDuration.setText(Integer.toString(workout.getDuration()));

        return view;
    }
}