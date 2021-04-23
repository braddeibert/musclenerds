package com.example.musclenerds;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.MotivationalQuote;
import com.example.musclenerds.model.TrackedWorkout;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.model.WorkoutExercise;
import com.example.musclenerds.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WorkoutHistory";
    // TODO: Rename and change types of p
    //  arameters
    private String mParam1;
    private String mParam2;

    private HomeViewModel homeViewModel;
    private AppDatabase mDb;

    public WorkoutHistory() {
        // Required empty public constructor
    }

//    public void onClick(View v){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutHistory newInstance(String param1, String param2) {
        WorkoutHistory fragment = new WorkoutHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//if (getArguments() != null) {
  //      mParam1 = getArguments().getString(ARG_PARAM1);
    //    mParam2 = getArguments().getString(ARG_PARAM2);
   // }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDb = AppDatabase.getInstance(getContext());
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workout_history, container, false);

        CalendarView calendarView=(CalendarView) root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Log.d("cal_log", "time: " + dayOfMonth);
                Date date = new Date(year, month, dayOfMonth);

                Calendar converter = Calendar.getInstance();
                converter.set(year, month, dayOfMonth);
                long timeStamp = date.getTime();

                date = new Date(year, month, dayOfMonth);
                long testTimeStamp = date.getTime();

                Log.d("cal_log", "time: " + timeStamp);
                Log.d("cal_log", "test time: " + testTimeStamp);
                String time = String.valueOf(timeStamp);
                String testTime = String.valueOf(testTimeStamp);
                Log.d("cal_log", "time: " + time);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<TrackedWorkout> allDayWorkouts = mDb.trackedWorkoutDAO().findByDate(testTime);
                        if(allDayWorkouts.size() < 1) {
                            TrackedWorkout testworkout = new TrackedWorkout(1, 20, String.valueOf(testTimeStamp));
                            mDb.trackedWorkoutDAO().insert(testworkout);
                        }
                        allDayWorkouts = mDb.trackedWorkoutDAO().findByDate(time);
                        Log.d("cal_log", "size: " + allDayWorkouts.size());

                        Log.d("cal_log", "workouts: " + allDayWorkouts.get(0).getDateCompleted());
                        TextView workoutView = root.findViewById(R.id.WorkoutTextView);

                        Workout curr_work = mDb.workoutDAO().findById(allDayWorkouts.get(0).getW_ID());

                        final String workoutName = curr_work.getName() + "\n" + curr_work.getDescription();

                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                workoutView.setText(workoutName);

                            }
                        });
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return root;
    }
}