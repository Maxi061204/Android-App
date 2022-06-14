package app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import app.api.state.StateManager;
import app.ui.MapScreen;
import app.ui.SplashScreen;

public class MainActivity extends AppCompatActivity {
    private StateManager stateManager;

    private static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = MainActivity.this;
        stateManager = StateManager.getInstance();

        new MapScreen();
    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }
}