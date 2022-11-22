package app.ui;

import android.widget.Button;
import android.widget.TextView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;
import app.ui.utils.Utils;

public class RightQuizAnswerScreen {
    private MainActivity instance;

    private TextView richtigeAntwort;
    private Button back, next;

    public RightQuizAnswerScreen() {
        instance = MainActivity.getInstance();
        run();
    }

    private void run(){
        instance.runOnUiThread(() -> instance.setContentView(R.layout.false_quiz_answer_screen));

        instance.runOnUiThread(() -> {
            richtigeAntwort = instance.findViewById(R.id.richtige_antwort);
            back = instance.findViewById(R.id.back);
            next = instance.findViewById(R.id.next);

            richtigeAntwort.setText("Richtig!");
        });
        back.setOnClickListener(event -> {
            new MapScreen();
        });
        next.setOnClickListener(event -> {
            new QuizScreen();
        });
    };
}