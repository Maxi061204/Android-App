package app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import app.api.state.StateManager;
import app.ui.LoginAccountScreen;
import app.ui.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private StateManager stateManager;

    private static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = MainActivity.this;
        stateManager = StateManager.getInstance();

       new LoginAccountScreen();

        // Utils.writeToFile(INSTANCE.getApplicationContext(), "test.txt", "test");

        // Toast.makeText(INSTANCE.getApplicationContext(), "Result: " + Utils.readFromFile(INSTANCE.getApplicationContext(), "test.txt"), Toast.LENGTH_SHORT).show();
    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }
}