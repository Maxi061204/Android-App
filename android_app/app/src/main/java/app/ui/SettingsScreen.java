package app.ui;


import android.widget.Button;
import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class SettingsScreen {

    Button ButtonPasswort, ButtonUsername, ButtonZoomPlus, ButtonZoomMinus, ButtonMap;

    private MainActivity instance;

    public SettingsScreen(){
        instance = MainActivity.getInstance();
        instance.runOnUiThread(() -> instance.setContentView(R.layout.settings_layout));

        run();

    }

    private void run(){

        ButtonPasswort.setOnClickListener(event -> {

        });

        ButtonUsername.setOnClickListener(event -> {


        });

        ButtonZoomPlus.setOnClickListener(event -> {

        });

        ButtonMap.setOnClickListener(event -> {
            new MapScreen();
        });
    }

}

