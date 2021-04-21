package com.example.musclenerds.ui.workoutview;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclenerds.R;

public class WorkoutCatalog extends Fragment {

    private WorkoutCatalogViewModel mViewModel;

    public static WorkoutCatalog newInstance() {
        return new WorkoutCatalog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.workout_catalog_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WorkoutCatalogViewModel.class);
        // TODO: Use the ViewModel
    }

}