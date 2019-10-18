package com.example.rescue6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.rescue6.model.SensorDataObject;
import com.example.rescue6.servicenow.AsyncTaskTableTes4;
import com.example.rescue6.sqlite.SensorReaderDbHelper;
import com.example.rescue6.ui.newmisson.NewMissionDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NewMissionDialogFragment.OnFragmentInteractionListener, SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;

    // capture sensor details when there is an active mission
    private SensorManager manager;
    private Sensor accSensor;
    private Sensor lightSensor;

    private CountDownTimer missionTimer;
    private SensorDataObject sensorDO;
    private SensorReaderDbHelper dbHelper;

    private Button missionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SensorReaderDbHelper(this);

        // An action button for the activity which points to the main pusrpose of the app. Bottom right corner.
        missionButton = findViewById(R.id.fab);
        missionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMissionDialogFragment.newInstance().show(getSupportFragmentManager(), NewMissionDialogFragment.class.getSimpleName());
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onStartMission(int objectCount, int minutes) {

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);

        manager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Toast.makeText(this, "Mission started for " + minutes + " mins to find " + objectCount + " objects", Toast.LENGTH_SHORT).show();
        sensorDO = new SensorDataObject();


        missionTimer = new CountDownTimer(minutes * 60 * 1000, 60 * 1000) {

            @Override
            public void onTick(long l) {
                // will be called every interval
                // save to SQLite
                SensorDataObject sdo = sensorDO;
                sensorDO = new SensorDataObject();

                long id = dbHelper.insertSensorData(sdo);
                Log.i(HomeActivity.class.getSimpleName(), "Save sensor data " + sdo.toString());
                missionButton.setText("Stop Misson("+l/(1000 * 60)+")");

                // send to service now
                new AsyncTaskTableTes4(sdo).execute();
            }

            @Override
            public void onFinish() {
                manager.unregisterListener(HomeActivity.this, accSensor);
                manager.unregisterListener(HomeActivity.this, lightSensor);
                missionTimer = null;

                missionButton.setText("Start New Mission");
            }
        };
        missionTimer.start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // update the sensor object. the object carries latest sensor value by the time it saves to the database.
        float[] values = sensorEvent.values;
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                sensorDO.setAccx(values[0]);
                sensorDO.setAccy(values[1]);
                sensorDO.setAccz(values[2]);
                break;
            case Sensor.TYPE_LIGHT:
                sensorDO.setLight(values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(missionTimer != null){
            missionTimer.cancel();

            manager.unregisterListener(HomeActivity.this, accSensor);
            manager.unregisterListener(HomeActivity.this, lightSensor);
            missionTimer = null;
        }
    }


}
