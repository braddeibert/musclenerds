package com.example.musclenerds.ui.Adapters;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.MainActivity;
import com.example.musclenerds.R;
import com.example.musclenerds.ui.models.Exercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Exercise> exercises;
    private LayoutInflater inflater;

    //Adapter that gets the exercises ready to display in the recyclerview

    //Initialize variables
    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_exercise, parent, false);

        final CustomViewHolder myHolder = new CustomViewHolder(view);

        //This is where I'm setting the buttons for now, I haven't figured out yet how to actually switch over to a new
        //fragment exercise_fragment. Right now it's just making a pop up of it that's a little janky, although, it could work
        //out if you can display the page a little better.

        Dialog myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.exercise_fragment);

        myHolder.ivExercise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                myDialog.show();
                return false;
            }
        });

        return myHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.tvExerciseName.setText(exercise.exerciseName);
        Picasso.get().load(exercise.imageUrl).into(holder.ivExercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ivExercise;
        public TextView tvExerciseName;

        public CustomViewHolder(View itemView){
            super(itemView);
            tvExerciseName = (TextView) itemView.findViewById(R.id.tvExerciseName);
            ivExercise = (ImageButton) itemView.findViewById(R.id.ivExercise);
            }
        }
    }