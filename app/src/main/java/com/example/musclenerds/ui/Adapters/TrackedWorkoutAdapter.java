package com.example.musclenerds.ui.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.model.TrackedWorkout;
import com.example.musclenerds.model.Workout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrackedWorkoutAdapter extends RecyclerView.Adapter<TrackedWorkoutAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Workout> workout;
    private LayoutInflater inflater;




    public TrackedWorkoutAdapter(Context context, ArrayList<Workout> workout) {
        this.context = context;
        this.workout = workout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.current_exercise, parent, false);

        final CustomViewHolder myHolder = new CustomViewHolder(view);

        Dialog currentDialog = new Dialog(context);
        currentDialog.setContentView(R.layout.exercise_fragment);

        myHolder.ivCurrentExercise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                currentDialog.show();
                return false;
            }
        });

        return myHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Workout currentWorkout = workout.get(position);
        holder.tvCurrentExerciseName.setText(currentWorkout.getName());
            }

    @Override
    public int getItemCount() {
        return workout.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ivCurrentExercise;
        public TextView tvCurrentExerciseName;

        public CustomViewHolder(View itemView){
            super(itemView);
            tvCurrentExerciseName = (TextView) itemView.findViewById(R.id.current_exercise_name);
            ivCurrentExercise = (ImageButton) itemView.findViewById(R.id.current_exercise_image);
        }
    }
}
