package app.ui;

import android.widget.ImageView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class SplashScreen {

    private MainActivity instance;

    public SplashScreen(){
        instance = MainActivity.getInstance();

        instance.runOnUiThread(() -> {
            this.instance.setContentView(R.layout.spash_screen_layout);
        });

        run();
    }

    private void run(){

    }
}
