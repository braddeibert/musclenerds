package com.example.musclenerds.ui.workoutcatalog.WorkoutAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.exerciseview.IndividualExerciseFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Workout> workouts;
    private LayoutInflater inflater;

    //Adapter that gets the exercises ready to display in the recyclerview

    //Initialize variables
    public WorkoutAdapter(Context context, ArrayList<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.single_workout, parent, false);

        final CustomViewHolder myHolder = new CustomViewHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        holder.tvWorkoutName.setText(workout.getName());
        Picasso.get().load("https://www.freeiconspng.com/uploads/displaying-19-images-for--workout-icon-png-0.png").resize(100, 80).into(holder.ivWorkout);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ivWorkout;
        public TextView tvWorkoutName;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvWorkoutName = (TextView) itemView.findViewById(R.id.tvWorkoutName);
            ivWorkout = (ImageButton) itemView.findViewById(R.id.ivWorkout);

            //Set buttons for each imageview, passing the exercise name through

            ivWorkout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        return false;
                    }
                    return false;
                }
            });
        }
    }
}