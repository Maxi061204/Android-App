package app.ui;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class SplashScreen {
    Button blau, rot, lila ;
    private MainActivity instance;

    public SplashScreen(){
        instance = MainActivity.getInstance();

        instance.runOnUiThread(() -> {
            this.instance.setContentView(R.layout.spash_screen_layout);

            blau = this.instance.findViewById(R.id.button_send);
            rot = this.instance.findViewById(R.id.button_send4);
            lila = this.instance.findViewById(R.id.button_send5);


            blau.setOnClickListener(event -> {
                new MapScreen();
            });


        });

        run();
    }
    private void run(){

    }
}
