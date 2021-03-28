package com.example.musclenerds;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.musclenerds.database.AppDatabase;
import com.example.musclenerds.database.AppExecutors;
import com.example.musclenerds.database.MotivationalQuoteDAO;
import com.example.musclenerds.model.Exercise;
import com.example.musclenerds.model.MotivationalQuote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
                R.id.drawer_home, R.id.drawer_workouts, R.id.drawer_exercises, R.id.drawer_tracking, R.id.drawer_hr_monitor)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        mDb = AppDatabase.getInstance(getApplicationContext()); // let the database handle creating a singleton instance. Must be done in an activity or something that extends an activity.

        //then use the executor to handle the execution.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //let's just refresh the static data every time.
                //see functions at bottom of file.
                //refreshDatabase();
                refreshDatabase();


                //mDb.clearAllTables();
            }
        });
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

    public void generateExercises() {
        Exercise newExercise = new Exercise("Sit-Up",
                "Lie down on your back. bend your legs and stabalize your lower bodey. Cross your hands to opposite shoulders, or place them behind your ears without pulling on your neck. Lift your head and shoulder blades from the ground, Exhale as you rise. Lower, returning to your starting point, exhale as you lower. Repeat.",
                "abdominal muscles",
                4,
                5,
                "none",
                "none for now");
        mDb.exerciseDAO().insert(newExercise);
        newExercise = new Exercise("Push-Up",
                "Get down on all fours, placing your hands slightly wider than your shoulders. Straighten your arms and legs. Lower your body until your chest nearly touches the floor. Pause, then push yourself back up. Repeat.",
                "triceps",
                4,
                5,
                "none",
                "none for now");
        mDb.exerciseDAO().insert(newExercise);
    }

    public void refreshDatabase() {
        List<MotivationalQuote> quotes = mDb.quoteDao().getAll();
        List<Exercise> exercises = mDb.exerciseDAO().getAll();

        if(quotes.size() > 0)
        for(int i = 0; i < quotes.size(); i++) {
            mDb.quoteDao().delete(quotes.get(i));
        }

        if(exercises.size() > 0)
        for(int i = 0; i < exercises.size(); i++) {
            mDb.exerciseDAO().delete(exercises.get(i));
        }

        generateQuotes();
        generateExercises();

        quotes = mDb.quoteDao().getAll();
        exercises = mDb.exerciseDAO().getAll();

        Log.d("AppData_LOG", "Fetched " + quotes.size() + " quotes:");
        for(int i = 0; i < quotes.size(); i++) {
            Log.d("AppData_LOG", "id: " + quotes.get(i).getId() + " text: " + quotes.get(i).getText());
        }

        Log.d("AppData_LOG", "Fetched " + exercises.size() + " exercises:");
        for(int i = 0; i < exercises.size(); i++) {
            Log.d("AppData_LOG", "id: " + exercises.get(i).getId() + " name: " + exercises.get(i).getName());
        }
    }
}