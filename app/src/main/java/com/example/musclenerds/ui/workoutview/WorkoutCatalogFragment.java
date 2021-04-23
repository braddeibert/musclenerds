package com.example.musclenerds.ui.workoutview;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.WorkoutDAO;
import com.example.musclenerds.model.Muscle;
import com.example.musclenerds.model.MuscleGroup;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.workoutview.adapters.WorkoutAdapter;
import com.example.musclenerds.ui.workoutview.adapters.WorkoutsByMGAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutCatalogFragment extends Fragment {

    private List<MuscleGroup> muscleGroups;
    private List<List<Workout>> workoutsByMg;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected WorkoutsByMGAdapter mAdapter;

    public static WorkoutCatalogFragment newInstance() {
        return new WorkoutCatalogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // fetch workout dao for accessing data, get all exercises from it and feed to adapter
        AsyncTask.execute(new Runnable() {
              @Override
              public void run() {
                  workoutsByMg = new ArrayList<List<Workout>>();

                  AppDatabase mDb = AppDatabase.getInstance(getContext());
                  muscleGroups = mDb.muscleGroupDAO().getAll();

                  for (MuscleGroup mg : muscleGroups) {
                      List<Workout> curr = mDb.workoutDAO().findByMuscleGroupId(mg.getId());
                      workoutsByMg.add(curr);
                  }

                  for (int i = 0; i < workoutsByMg.size(); i++) {
                      if (workoutsByMg.get(i).size() == 0) {
                          muscleGroups.remove(muscleGroups.get(i));
                          workoutsByMg.remove(workoutsByMg.get(i));
                      }
                  }

                  mAdapter = new WorkoutsByMGAdapter(muscleGroups, workoutsByMg, rootView.getContext());

                  new Handler(Looper.getMainLooper()).post(new Runnable(){
                      @Override
                      public void run() {
                          mRecyclerView.setAdapter(mAdapter);
                          mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                      }
                  });
              }
          }
        );

        return rootView;
    }

}