package app.ui;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import app.MainActivity;
import app.api.quiz.SpecificQuiz;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class SplashScreen {
    Button blau, rot, lila ;
    private MainActivity instance;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    public SplashScreen(){
        instance = MainActivity.getInstance();

        instance.runOnUiThread(() -> {
            this.instance.setContentView(R.layout.spash_screen_layout);

            //Den buttons wird eine id zugewiesen
            blau = this.instance.findViewById(R.id.button_send);
            rot = this.instance.findViewById(R.id.button_send4);
            lila = this.instance.findViewById(R.id.button_send5);





            blau.setOnClickListener(event -> {
                //Wenn auf den Button gecklickt wird passiert folgendes
                new MapScreen();
            });

            lila.setOnClickListener(event -> {
                //Wenn auf den Button gecklickt wird passiert folgendes
                new MapScreen();
            });

            rot.setOnClickListener(event -> {
                //Wenn auf den Button gecklickt wird passiert folgendes
                new RaetselScreen("Domplatz");
            });

        });

        run();
    }
    private void run(){

    }
}
