package app.ui;


import android.widget.Button;
import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class SettingsScreen {

    Button ButtonPasswort, ButtonUsername, ButtonZoomPlus, ButtonZoomMinus, ButtonMap;

    private MainActivity instance;

    public SettingsScreen(){
        instance = MainActivity.getInstance();
        instance.runOnUiThread(() -> {instance.setContentView(R.layout.settings_layout);
        ButtonPasswort = this.instance.findViewById(R.id.ButtonPasswort);
            ButtonZoomPlus = this.instance.findViewById(R.id.ButtonZoomPlus);
            ButtonZoomMinus = this.instance.findViewById(R.id.ButtonZoomMinus);
            ButtonUsername= this.instance.findViewById(R.id.ButtonUsername);
            ButtonMap = this.instance.findViewById(R.id.ButtonMap);
        });

        run();

    }

    private void run(){

        ButtonPasswort.setOnClickListener(event -> {

        });

        ButtonUsername.setOnClickListener(event -> {


        });

        ButtonZoomPlus.setOnClickListener(event -> {
        MapScreen.ZoomErhöhen();
        });

        ButtonZoomMinus.setOnClickListener(event ->{
            MapScreen.ZoomVeringern();
        });

        ButtonMap.setOnClickListener(event -> {
            new MapScreen();
        });
    }

}

