package com.example.musclenerds.ui.exerciseview.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.ui.exerciseview.MuscleExercises;

import java.util.ArrayList;

public class MuscleGroupAdapter extends RecyclerView.Adapter<MuscleGroupAdapter.ViewHolder>{

    public ArrayList<MuscleExercises> muscleExercises;
    private Context context;
    private LayoutInflater layoutInflater;


    public MuscleGroupAdapter(ArrayList<MuscleExercises> muscleExercises, Context context) {
        this.muscleExercises = muscleExercises;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.single_muscle_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.recyclerView.setAdapter(new ExerciseAdapter(context, muscleExercises.get(position).exercises));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setHasFixedSize(true);
        holder.tvHeading.setText(String.valueOf(muscleExercises.get(position).muscleName));
    }


    @Override
    public int getItemCount() {
        return muscleExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public TextView tvHeading;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rvExercises);
            tvHeading = (TextView) itemView.findViewById(R.id.tvMuscleName);
        }
    }

}
