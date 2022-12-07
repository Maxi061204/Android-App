package app.ui;

import android.widget.Button;
import android.widget.TextView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class ZeitAbgelaufenScreen {
    private MainActivity instance;

    private TextView richtigeAntwort;
    private Button back, next;

    private String richtigeAntwortString;

    public ZeitAbgelaufenScreen(String richtigeAntwort) {
        instance = MainActivity.getInstance();
        this.richtigeAntwortString = richtigeAntwort;

        run();
        }

    private void run(){
        instance.runOnUiThread(() -> instance.setContentView(R.layout.false_quiz_answer_screen));

        instance.runOnUiThread(() -> {
            richtigeAntwort = instance.findViewById(R.id.richtige_antwort);
            back = instance.findViewById(R.id.back);
            next = instance.findViewById(R.id.next);

            richtigeAntwort.setText("Die Zeit ist abgelaufen!\nRichtige Antwort: " + this.richtigeAntwortString);
        });
        back.setOnClickListener(event -> {
            new MapScreen();
        });
        next.setOnClickListener(event -> {
            new QuizScreen();
        });
    };
}
