package com.example.musclenerds.ui.exerciseview;

import android.app.AppComponentFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.musclenerds.R;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.MotivationalQuote;
import com.example.musclenerds.ui.Adapters.ExerciseAdapter;
import com.example.musclenerds.ui.Adapters.MuscleGroupAdapter;
import com.example.musclenerds.ui.models.Exercise;
import com.example.musclenerds.ui.models.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

public class exercise_view extends Fragment {

    private AppDatabase mDb; //create the database var.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = AppDatabase.getInstance(getContext()); // tell the database to create itself.

        View view = inflater.inflate(R.layout.exercise_view_fragment, container, false);

        ArrayList<MuscleGroup> muscleGroups = prepareData();

        MuscleGroupAdapter muscleGroupAdapter = new MuscleGroupAdapter(muscleGroups, view.getContext());

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        RecyclerView rvMuscleGroup = view.findViewById(R.id.rvSubject);

        rvMuscleGroup.setHasFixedSize(true);
        rvMuscleGroup.setLayoutManager(manager);
        rvMuscleGroup.setAdapter(muscleGroupAdapter);

        return view;
    }

    //Creates the data to be displayed in the recyclerview, a list of msucles with a list of exercises.

    private ArrayList<MuscleGroup> prepareData() {

        // create a background thread.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create a list of exercises.
                // Use the type of Exercise defined in the database though.
                //List<Exercise> exercises = mDb.exerciseDAO().getAll();
            }
        });
        //New data
        ArrayList<MuscleGroup> muscles = new ArrayList<MuscleGroup>();

        //Muscle group with an array of exercises.

        //Add a new muscle group to add a new row to the recyclerview

        //Add a new exercise to add a new column to the row
        MuscleGroup chest = new MuscleGroup();
        chest.id = 1;
        chest.muscleName = "Chest";
        chest.exercises = new ArrayList<Exercise>();

        Exercise exercise1 = new Exercise();
        exercise1.id = 1;
        exercise1.exerciseName = "Chest Bump";
        //Image URL for use with Picasso, this is what shows up in the recycler view
        exercise1.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise2 = new Exercise();
        exercise2.id = 2;
        exercise2.exerciseName = "Chest Plow";
        exercise2.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise3 = new Exercise();
        exercise3.id = 3;
        exercise3.exerciseName = "Chest Fly";
        exercise3.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise4 = new Exercise();
        exercise4.id = 4;
        exercise4.exerciseName = "Chest Drum";
        exercise4.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise5 = new Exercise();
        exercise5.id = 5;
        exercise5.exerciseName = "Chest Rub";
        exercise5.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise6 = new Exercise();
        exercise6.id = 6;
        exercise6.exerciseName = "Chest Punch";
        exercise6.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise7 = new Exercise();
        exercise7.id = 7;
        exercise7.exerciseName = "Chest Bongos";
        exercise7.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        chest.exercises.add(exercise1);
        chest.exercises.add(exercise2);
        chest.exercises.add(exercise3);
        chest.exercises.add(exercise4);
        chest.exercises.add(exercise5);
        chest.exercises.add(exercise6);
        chest.exercises.add(exercise7);

        MuscleGroup back = new MuscleGroup();
        back.id = 2;
        back.muscleName = "Back";
        back.exercises = new ArrayList<Exercise>();

        Exercise exercise8 = new Exercise();
        exercise8.id = 1;
        exercise8.exerciseName = "Back Massage";
        exercise8.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise9 = new Exercise();
        exercise9.id = 2;
        exercise9.exerciseName = "Back Up";
        exercise9.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise10 = new Exercise();
        exercise10.id = 3;
        exercise10.exerciseName = "Back Stroke";
        exercise10.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise11 = new Exercise();
        exercise11.id = 4;
        exercise11.exerciseName = "Back in Black";
        exercise11.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise12 = new Exercise();
        exercise12.id = 5;
        exercise12.exerciseName = "Back in the USA";
        exercise12.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise13 = new Exercise();
        exercise13.id = 6;
        exercise13.exerciseName = "Back Stuff";
        exercise13.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise14 = new Exercise();
        exercise14.id = 7;
        exercise14.exerciseName = "Back Bongos";
        exercise14.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        back.exercises.add(exercise8);
        back.exercises.add(exercise9);
        back.exercises.add(exercise10);
        back.exercises.add(exercise11);
        back.exercises.add(exercise12);
        back.exercises.add(exercise13);
        back.exercises.add(exercise14);

        MuscleGroup legs = new MuscleGroup();
        legs.id = 3;
        legs.muscleName = "Legs";
        legs.exercises = new ArrayList<Exercise>();

        Exercise exercise15 = new Exercise();
        exercise15.id = 1;
        exercise15.exerciseName = "Leg Pushup";
        exercise15.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise16 = new Exercise();
        exercise16.id = 2;
        exercise16.exerciseName = "Leg roll";
        exercise16.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise17 = new Exercise();
        exercise17.id = 3;
        exercise17.exerciseName = "Leg Thing";
        exercise17.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise18 = new Exercise();
        exercise18.id = 4;
        exercise18.exerciseName = "Leg Crunch";
        exercise18.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise19 = new Exercise();
        exercise19.id = 5;
        exercise19.exerciseName = "Lego my Ego";
        exercise19.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise20 = new Exercise();
        exercise20.id = 6;
        exercise20.exerciseName = "Legolas";
        exercise20.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise21 = new Exercise();
        exercise21.id = 7;
        exercise21.exerciseName = "Lego Land";
        exercise21.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        legs.exercises.add(exercise15);
        legs.exercises.add(exercise16);
        legs.exercises.add(exercise17);
        legs.exercises.add(exercise18);
        legs.exercises.add(exercise19);
        legs.exercises.add(exercise20);
        legs.exercises.add(exercise21);

        MuscleGroup arms = new MuscleGroup();
        arms.id = 4;
        arms.muscleName = "Arms";
        arms.exercises = new ArrayList<Exercise>();

        Exercise exercise22 = new Exercise();
        exercise22.id = 1;
        exercise22.exerciseName = "Crunchy Arms";
        exercise22.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise23 = new Exercise();
        exercise23.id = 2;
        exercise23.exerciseName = "Armageddon";
        exercise23.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise24 = new Exercise();
        exercise24.id = 3;
        exercise24.exerciseName = "Pull Ups";
        exercise24.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise25 = new Exercise();
        exercise25.id = 4;
        exercise25.exerciseName = "Army Time";
        exercise25.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise26 = new Exercise();
        exercise26.id = 5;
        exercise26.exerciseName = "High Five";
        exercise26.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise27 = new Exercise();
        exercise27.id = 6;
        exercise27.exerciseName = "Arm Thing";
        exercise27.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        Exercise exercise28 = new Exercise();
        exercise28.id = 7;
        exercise28.exerciseName = "Arm Land";
        exercise28.imageUrl = "http://images.esellerpro.com/2347/I/919/71/GYMDUMBELL15kga.jpg";

        arms.exercises.add(exercise22);
        arms.exercises.add(exercise23);
        arms.exercises.add(exercise24);
        arms.exercises.add(exercise25);
        arms.exercises.add(exercise26);
        arms.exercises.add(exercise27);
        arms.exercises.add(exercise28);

        muscles.add(chest);
        muscles.add(legs);
        muscles.add(back);
        muscles.add(arms);

        return muscles;
    }


}