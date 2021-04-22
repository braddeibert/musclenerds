package com.example.musclenerds.ui.workoutview.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.WorkoutDAO;
import com.example.musclenerds.model.MuscleGroup;
import com.example.musclenerds.model.Workout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class WorkoutsByMGAdapter extends RecyclerView.Adapter<WorkoutsByMGAdapter.ViewHolder> {

    private List<MuscleGroup> muscleGroups;
    private List<List<Workout>> workouts;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.tvMuscleGroup);
            recyclerView = (RecyclerView) view.findViewById(R.id.rvWorkouts);
        }

        public TextView getTextView() {
            return textView;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }
    }

    public WorkoutsByMGAdapter(List<MuscleGroup> data, List<List<Workout>> workoutsByMg, Context c) {
        context = c;
        muscleGroups = data;
        workouts = workoutsByMg;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WorkoutsByMGAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_workouts_by_mg, viewGroup, false);

        return new WorkoutsByMGAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(WorkoutsByMGAdapter.ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getRecyclerView().setAdapter(new WorkoutAdapter(workouts.get(position)));
        viewHolder.getRecyclerView().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        viewHolder.getRecyclerView().setHasFixedSize(true);
        viewHolder.getTextView().setText(muscleGroups.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return workouts.size();
    }

}
