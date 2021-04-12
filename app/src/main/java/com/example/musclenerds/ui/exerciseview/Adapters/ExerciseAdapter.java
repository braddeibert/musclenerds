package com.example.musclenerds.ui.exerciseview.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.ui.exerciseview.IndividualExerciseFragment;
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

        return myHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.tvExerciseName.setText(exercise.getName());
        Picasso.get().load("http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg").into(holder.ivExercise);
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

            //Set buttons for each imageview, passing the exercise name through

            ivExercise.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment myFragment = new IndividualExerciseFragment(tvExerciseName.getText().toString());
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.exercise_view, myFragment).addToBackStack(null).commit();
                    return false;
                }
            });

            }
        }
    }