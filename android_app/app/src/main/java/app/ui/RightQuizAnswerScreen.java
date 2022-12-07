package app.ui;

import android.widget.Button;
import android.widget.TextView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class RightQuizAnswerScreen {
    private MainActivity instance;

    private TextView richtig;
    private Button back, next;

    public RightQuizAnswerScreen() {
        instance = MainActivity.getInstance();
        run();
    }

    private void run(){
        instance.runOnUiThread(() -> instance.setContentView(R.layout.right_quiz_answer_screen));

        instance.runOnUiThread(() -> {
            richtig = instance.findViewById(R.id.richtig);
            back = instance.findViewById(R.id.back);
            next = instance.findViewById(R.id.next);

            richtig.setText("Richtig!");
        });
        back.setOnClickListener(event -> {
            new MapScreen();
        });
        next.setOnClickListener(event -> {
            new QuizScreen();
        });
    };
}
