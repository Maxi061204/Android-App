package app;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import app.api.state.StateManager;
import app.ui.LoginAccountScreen;

public class MainActivity extends AppCompatActivity {
    private StateManager stateManager;

    private static MainActivity INSTANCE;
    private static Bundle savedInstanceState;

    private final int LOCATION_REFRESH_TIME = 15000;
    private final int LOCATION_REFRESH_DISTANCE = 50;

    private static Location location;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = MainActivity.this;
        stateManager = StateManager.getInstance();

        final LocationListener mLocationListener = loc -> {
            location = loc;
        };

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);

       new LoginAccountScreen();

        // Utils.writeToFile(INSTANCE.getApplicationContext(), "test.txt", "test");

        // Toast.makeText(INSTANCE.getApplicationContext(), "Result: " + Utils.readFromFile(INSTANCE.getApplicationContext(), "test.txt"), Toast.LENGTH_SHORT).show();
    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }

    public static Bundle getSavedInstanceState(){return savedInstanceState;}

    public static Location getLocation() {
        return location;
    }
}