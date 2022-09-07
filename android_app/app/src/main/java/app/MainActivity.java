package app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import app.api.state.StateManager;
import app.ui.LoginAccountScreen;
import app.ui.SplashScreen;
import app.ui.MapScreen;

public class MainActivity extends AppCompatActivity {
    private StateManager stateManager;

    private static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = MainActivity.this;
        stateManager = StateManager.getInstance();

        new LoginAccountScreen();
    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }


    public void sendMessage(View view) {
        new MapScreen();
    }
}