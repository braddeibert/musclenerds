package com.example.musclenerds;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.musclenerds.model.Equipment;
import com.example.musclenerds.model.ExerciseEquipment;
import com.example.musclenerds.model.Muscle;
import com.example.musclenerds.model.MuscleGroup;
import com.example.musclenerds.model.MuscleGroups;
import com.example.musclenerds.model.TrackedSet;
import com.example.musclenerds.model.TrackedWorkout;
import com.example.musclenerds.model.Workout;
import com.example.musclenerds.model.WorkoutExercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.MotivationalQuote;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private AppDatabase mDb; // make a reference to the database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.drawer_home, R.id.drawer_workouts, R.id.drawer_exercises, R.id.drawer_tracking, R.id.drawer_history)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        mDb = AppDatabase.getInstance(getApplicationContext()); // let the database handle creating a singleton instance. Must be done in an activity or something that extends an activity.
        new JsonTask().execute("https://raw.githubusercontent.com/braddeibert/musclenerds/master/test_data.json");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getQuote(View view) {
        Random rand = new Random();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<MotivationalQuote> quotes = mDb.quoteDao().getAll(); //get all quotes.
                int index = rand.nextInt(quotes.size());
                Snackbar.make(view, "' " + quotes.get(index).getText() + "' --" + quotes.get(index).getAuthor(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // Following functions are for generating static data in the database.
    public void generateQuotes() {
        MotivationalQuote newQuote = new MotivationalQuote("Today I will do what others won’t, so tomorrow I can accomplish what others can’t.", "Jerry Rice");
        mDb.quoteDao().insert(newQuote); // insert a quote.

        newQuote = new MotivationalQuote("Do something today that your future self will thank you for.", "Unknown");
        mDb.quoteDao().insert(newQuote); // insert a quote.

        newQuote = new MotivationalQuote("We are what we repeatedly do. Excellence then is not an act but a habit.", "Aristotle");
        mDb.quoteDao().insert(newQuote); // insert a quote.

        newQuote = new MotivationalQuote("No matter how slow you go, you are still lapping everybody on the couch.", "Unknown");
        mDb.quoteDao().insert(newQuote); // insert a quote.

        newQuote = new MotivationalQuote("Sweat is fat crying.", "Unknown");
        mDb.quoteDao().insert(newQuote); // insert a quote.
    }

    String txtJson;
    ProgressDialog pd;

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            txtJson = result;
            //Log.d("json_log", result);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                    JSONObject jObject;
                    JSONArray exercises, exerciseEquipment, equipment, muscles, muscleGroups, muscleGroup, workouts, workoutExercises, trackedWorkouts, trackedSets;
                    //let's just refresh the static data every time.
                    //see functions at bottom of file.
                    //refreshDatabase();
                    //refreshDatabase();
                    mDb.clearAllTables();
                    generateQuotes();
                    // create a json object from the result.
                    try {
                        //create the json object. Then fetch all the arrays from it.
                        jObject = new JSONObject(result);
                        exercises = jObject.getJSONArray("EXERCISES");
                        exerciseEquipment = jObject.getJSONArray("EXERCISE_EQUIPMENT");
                        equipment = jObject.getJSONArray("EQUIPMENT");
                        muscles = jObject.getJSONArray("MUSCLES");
                        muscleGroups = jObject.getJSONArray("MUSCLE_GROUPS");
                        muscleGroup = jObject.getJSONArray("MUSCLE_GROUP");
                        workouts = jObject.getJSONArray("WORKOUTS");
                        workoutExercises = jObject.getJSONArray("WORKOUT_EXERCISES");
                        trackedWorkouts = jObject.getJSONArray("TRACKED_WORKOUTS");
                        trackedSets = jObject.getJSONArray("TRACKED_SETS");

                        //let's parse the arrays, order matters.
                        for (int i=0; i < equipment.length(); i++)
                        {
                            try {
                                JSONObject oneObject = equipment.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                String name = oneObject.getString("Name");
                                Log.d("exercise_log", name);
                                Equipment newEquipment = new Equipment(id, name);
                                mDb.equipmentDAO().insert(newEquipment);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the exercises
                        for (int i=0; i < exercises.length(); i++)
                        {
                            try {
                                JSONObject oneObject = exercises.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                String name = oneObject.getString("Name");
                                String type = oneObject.getString("Type");
                                String desc = oneObject.getString("Description");
                                Exercise newExercise = new Exercise(id, name, desc, type, "");
                                Log.d("exercise_log", newExercise.getName());
                                mDb.exerciseDAO().insert(newExercise);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the exerciseEquipment
                        for (int i=0; i < exerciseEquipment.length(); i++)
                        {
                            try {
                                JSONObject oneObject = exerciseEquipment.getJSONObject(i);
                                // Pulling items from the array
                                int e_id = oneObject.getInt("exid");
                                int eq_id = oneObject.getInt("eqid");
                                ExerciseEquipment newExerciseEquipment = new ExerciseEquipment(eq_id, e_id);
                                mDb.exerciseEquipmentDAO().insert(newExerciseEquipment);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the muscles
                        for (int i=0; i < muscles.length(); i++)
                        {
                            try {
                                JSONObject oneObject = muscles.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                String name = oneObject.getString("Name");
                                Muscle newMuscle = new Muscle(id, name);
                                mDb.muscleDAO().insert(newMuscle);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the muscleGroup
                        for (int i=0; i < muscleGroup.length(); i++)
                        {
                            try {
                                JSONObject oneObject = muscleGroup.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                String name = oneObject.getString("name");
                                MuscleGroup newMuscleGroup = new MuscleGroup(id, name);
                                mDb.muscleGroupDAO().insert(newMuscleGroup);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the muscleGroups
                        for (int i=0; i < muscleGroups.length(); i++)
                        {
                            try {
                                JSONObject oneObject = muscleGroups.getJSONObject(i);
                                // Pulling items from the array
                                int G_ID = oneObject.getInt("gid");
                                int M_ID = oneObject.getInt("mid");
                                MuscleGroups newMuscleGroups = new MuscleGroups(G_ID, M_ID);
                                mDb.muscleGroupsDAO().insert(newMuscleGroups);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the workouts
                        for (int i=0; i < workouts.length(); i++)
                        {
                            try {
                                JSONObject oneObject = workouts.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                int g_id = oneObject.getInt("mgid");
                                String name = oneObject.getString("Name");
                                String desc = oneObject.getString("Description");
                                int duration = oneObject.getInt("Duration");
                                Workout newWorkout = new Workout(id, name, desc, duration, g_id);
                                Log.d("exercise_log", "workout: " + newWorkout.getName());
                                mDb.workoutDAO().insert(newWorkout);
                            } catch (JSONException e) {
                                Log.d("exercise_log", "Error fetching workouts");
                            }
                        }
                        // Next parse the workoutExercises
                        for (int i=0; i < workoutExercises.length(); i++)
                        {
                            try {
                                JSONObject oneObject = workoutExercises.getJSONObject(i);
                                // Pulling items from the array
                                int w_id = oneObject.getInt("wid");
                                int e_id = oneObject.getInt("eid");
                                int sets = oneObject.getInt("sets");
                                int reps = oneObject.getInt("reps");
                                WorkoutExercise newWorkoutExercise = new WorkoutExercise(w_id, e_id, sets, reps);
                                mDb.workoutExerciseDAO().insert(newWorkoutExercise);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the trackedWorkouts
                        for (int i=0; i < trackedWorkouts.length(); i++)
                        {
                            try {
                                JSONObject oneObject = trackedWorkouts.getJSONObject(i);
                                // Pulling items from the array
                                int id = oneObject.getInt("id");
                                int w_id = oneObject.getInt("wid");
                                String date = oneObject.getString("date_completed");
                                int duration = oneObject.getInt("duration");
                                TrackedWorkout newTrackedWorkout = new TrackedWorkout(id, w_id, duration, date);
                                mDb.trackedWorkoutDAO().insert(newTrackedWorkout);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                        // Next parse the trackedSets
                        for (int i=0; i < trackedSets.length(); i++)
                        {
                            try {
                                JSONObject oneObject = trackedSets.getJSONObject(i);
                                // Pulling items from the array
                                int eid = oneObject.getInt("eid");
                                int tid = oneObject.getInt("tid");
                                int reps = oneObject.getInt("reps");
                                int weight = oneObject.getInt("weight");
                                int difficulty = oneObject.getInt("difficulty");
                                TrackedSet newTrackedSet = new TrackedSet(eid, tid, reps, weight, difficulty, "");
                                mDb.trackedSetDAO().insert(newTrackedSet);
                            } catch (JSONException e) {
                                // Oops
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}