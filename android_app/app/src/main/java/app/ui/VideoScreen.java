package app.ui;

import android.net.Uri;
import android.widget.VideoView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class VideoScreen {
    private MainActivity instance;

    private VideoView videoView;

    public VideoScreen(){
        this.instance = MainActivity.getInstance();

        run();
    }

    private void run(){
        this.instance.runOnUiThread(() -> {
            this.instance.setContentView(R.layout.video_screen);

            videoView = instance.findViewById(R.id.video_view);
            String videoPath = "android.resource://" + instance.getPackageName() + "/" + R.raw.bideo;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            videoView.start();
        });
    }

}
