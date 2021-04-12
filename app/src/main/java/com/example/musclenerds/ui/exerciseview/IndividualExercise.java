package com.example.musclenerds.ui.exerciseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.musclenerds.R;

//Fragment class for the Individual exercise information. The exercise view screen with the description an GIF

public class IndividualExercise extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exercise_fragment, container, false);

        return view;
    }
}
