package com.example.musclenerds.ui.workoutview.adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclenerds.R;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.ui.workoutview.WorkoutFragment;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<Workout> workouts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public WorkoutAdapter(List<Workout> data) {
        workouts = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_workout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(workouts.get(position).getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment workoutFragment = new WorkoutFragment(workouts.get(position));
                FragmentManager fragManager = activity.getSupportFragmentManager();

                fragManager.beginTransaction().replace(R.id.workout_catalog_view, workoutFragment).addToBackStack(null).commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return workouts.size();
    }

}
