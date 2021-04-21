package com.example.musclenerds.ui.workoutcatalog.WorkoutAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.ui.workoutcatalog.WorkoutMuscleExercises;

import java.util.ArrayList;

public class WorkoutMuscleGroupAdapter extends RecyclerView.Adapter<WorkoutMuscleGroupAdapter.ViewHolder>{

    public ArrayList<WorkoutMuscleExercises> muscleGroups;
    private Context context;
    private LayoutInflater layoutInflater;


    public WorkoutMuscleGroupAdapter(ArrayList<WorkoutMuscleExercises> muscleGroups, Context context) {
        this.muscleGroups = muscleGroups;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.single_workout_muscle_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.recyclerView.setAdapter(new WorkoutAdapter(context, muscleGroups.get(position).workouts));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setHasFixedSize(true);
        holder.tvHeading.setText(String.valueOf(muscleGroups.get(position).muscleName));
    }


    @Override
    public int getItemCount() {
        return muscleGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public TextView tvHeading;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rvWorkouts);
            tvHeading = (TextView) itemView.findViewById(R.id.tvWorkoutMuscleName);
        }
    }

}
