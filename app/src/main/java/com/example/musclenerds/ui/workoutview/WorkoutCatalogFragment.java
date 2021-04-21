package com.example.musclenerds.ui.workoutview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.WorkoutDAO;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.Adapters.WorkoutAdapter;

import java.util.List;

public class WorkoutCatalogFragment extends Fragment {

    private List<Workout> allWorkouts;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected WorkoutAdapter mAdapter;

    public static WorkoutCatalogFragment newInstance() {
        return new WorkoutCatalogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch workout dao for accessing data, get all exercises from it
        AsyncTask.execute(new Runnable() {
              @Override
              public void run() {
                  allWorkouts = AppDatabase.getInstance(getContext()).workoutDAO().getAll();
              }
          }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workout_catalog_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new WorkoutAdapter(allWorkouts);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

}