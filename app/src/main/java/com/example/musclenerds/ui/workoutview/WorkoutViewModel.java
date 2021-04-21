/* package com.example.musclenerds.ui.workoutview;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.WorkoutDAO;
import com.example.musclenerds.model.Workout;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private final WorkoutDAO workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutViewModel (Application application) {
        super(application);
        AppDatabase mDb = AppDatabase.getDatabase(application);
        workoutDao = mDb.workoutDAO();
        allWorkouts = workoutDao.getAll();
    }

    LiveData<List<Workout>> getAllWorkouts() { return allWorkouts; }

    public void insert(Workout workout) { workoutDao.insert(workout); }

}

*/

