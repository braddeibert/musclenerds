package com.example.musclenerds;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutHistory extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WorkoutHistory";
    // TODO: Rename and change types of p
    //  arameters
    private String mParam1;
    private String mParam2;

    Button button;

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

    public void replaceFragment(Fragment f) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.calender, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workout_history, container, false);
        button = v.findViewById(R.id.viewWorkout);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackedWorkout t = new TrackedWorkout();
                replaceFragment(t);
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}