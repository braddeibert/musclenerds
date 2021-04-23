package com.example.musclenerds;

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
import android.widget.TextView;

import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.TrackedWorkout;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.model.WorkoutExercise;
import com.example.musclenerds.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

        setRandomWorkouts();

        //grab the calendarView object
        CalendarView calendarView=(CalendarView) root.findViewById(R.id.calendarView);
        //the text view that will display the workout highlight on the ui
        TextView workoutView = root.findViewById(R.id.WorkoutTextView);

        Date currDate = new Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        long currTime = currDate.getTime();
        String currtimeString = String.valueOf(currTime);
        Log.d("cal_log", "time: " + Calendar.getInstance().get(Calendar.YEAR) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + ":" + currTime);

        //need to get the grab the current day info on creating the view.
        //a listener that we add after this chunk will handle grabbing info on
        //selected day being changed.
        setWorkoutHighlight(currtimeString, root);

        //attach a listener to the calendarView object.
        //this will fire when the selected date is changed.
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //create a date object based on the selected yea, day, month
                Date date = new Date(year, month, dayOfMonth);

                //get the time sends epoch
                long timeStamp = date.getTime();

                //store it in a string.
                String time = String.valueOf(timeStamp);
                Log.d("cal_log", "time: " + month);

                setWorkoutHighlight(time, root);

            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    public void setWorkoutHighlight(String time, View root) {
        TextView workoutView = root.findViewById(R.id.WorkoutTextView);
        //create a thread for db ops
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //string to store the workout info
                String workoutHighlight;
                //the list of workouts for a given day.
                List<TrackedWorkout> allDayWorkouts = mDb.trackedWorkoutDAO().findByDate(time);
                //if the workouts are empty, show a message instead of a workout.
                if(allDayWorkouts.size() < 1) {
                    workoutHighlight = "Nothing to show for this day, get to work!!";
                }
                //else, the list should be a list of workouts for the given day, ordered by most recent.
                //grab the first one on the list and show it's info.
                else {
                    Workout curr_work = mDb.workoutDAO().findById(allDayWorkouts.get(0).getW_ID());

                    workoutHighlight = curr_work.getName() + "\n" + curr_work.getDescription();

                    List<WorkoutExercise> workoutExercises = mDb.workoutExerciseDAO().findByW_ID(curr_work.getId());

                    //then make a list of all the exercises from the workoutExercises list.
                    List<Exercise> exercises = new ArrayList<Exercise>();
                    for(int i = 0; i < workoutExercises.size(); i++) {
                        exercises.add(mDb.exerciseDAO().findById(workoutExercises.get(i).getE_ID()));
                        workoutHighlight += "\n-" + mDb.exerciseDAO().findById(workoutExercises.get(i).getE_ID()).getName();
                        Log.d("cal_log", mDb.exerciseDAO().findById(workoutExercises.get(i).getE_ID()).getName());
                    }
                }

                //textView.seText() expects a final string.
                final String uiWorkoutHighlight = workoutHighlight;

                //create a thread to set the text of the textView object.
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        workoutView.setText(uiWorkoutHighlight);

                    }
                });
            }
        });
    }

    public void setRandomWorkouts() {
        //create a thread for db ops
        Random rand = new Random();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int year = 2021;
                int month = 3;
                    for(int day = 1; day < 30; day++) {
                        Date currDate = new Date(year, month, day);
                        long currTime = currDate.getTime();
                        String timeString = String.valueOf(currTime);
                        int workout = rand.nextInt(3) + 1;
                        Log.d("cal_log", "rand" + workout);
                        TrackedWorkout newTrackedWorkout = new TrackedWorkout(workout, 30, timeString);
                        mDb.trackedWorkoutDAO().insert(newTrackedWorkout);
                    }
            }
        });
    }
}