package app.ui;

import android.widget.Button;
import android.widget.TextView;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;
import app.api.quiz.GenericQuiz;
import app.ui.utils.Utils;
import java.util.Random;

public class QuizScreen {
    private MainActivity instance;

    private TextView frage;

    private Button eins, zwei, drei, vier;

    public QuizScreen(){

        instance = MainActivity.getInstance();

        run();
    }
    int randomnumber;
    private void run(){
        // Hier ist die Api-Anfrage in der GenericQuiz Klasse verpackt. Hier muss nur noch geprüft werden ob die Anfrage erfolgreich war.
        GenericQuiz.getRandomQuiz(quiz -> {
            if (quiz == null){
                Utils.showErrorMessage(instance, "Ein Fehler ist aufgetreten, als ein Quiz vom Server abgefraget werden sollte!", "");
                return;
            }

            instance.runOnUiThread(() -> {
                frage = instance.findViewById(R.id.tobi_frage);

                eins = instance.findViewById(R.id.tobi_button_eins);
                zwei = instance.findViewById(R.id.tobi_button_zwei);
                drei = instance.findViewById(R.id.tobi_button_drei);
                vier = instance.findViewById(R.id.tobi_button_vier);

                frage.setText(quiz.getQuestion());

                Random number = new Random();
                int randomnumber = number.nextInt(4);//je nach Zahl werden die Texte anders zugeordnet
                if(randomnumber == 0){
                    eins.setText(quiz.getWrongAnswers()[0]);
                    zwei.setText(quiz.getWrongAnswers()[1]);
                    drei.setText(quiz.getWrongAnswers()[2]);
                    vier.setText(quiz.getCorrectAnswer());}

                else{
                    if(randomnumber == 1) {
                        eins.setText(quiz.getWrongAnswers()[1]);
                        zwei.setText(quiz.getWrongAnswers()[2]);
                        drei.setText(quiz.getCorrectAnswer());
                        vier.setText(quiz.getWrongAnswers()[0]); }
                    else{
                        if(randomnumber == 2){
                            eins.setText(quiz.getWrongAnswers()[2]);
                            zwei.setText(quiz.getCorrectAnswer());
                            drei.setText(quiz.getWrongAnswers()[1]);
                            vier.setText(quiz.getWrongAnswers()[0]);}
                        else{
                            if(randomnumber == 3){
                                eins.setText(quiz.getCorrectAnswer());
                                zwei.setText(quiz.getWrongAnswers()[2]);
                                drei.setText(quiz.getWrongAnswers()[1]);
                                vier.setText(quiz.getWrongAnswers()[0]);}
                        }}}


            });

        });
    }
    int coins;
    private void answereins() {
        if (randomnumber == 3){
            coins=+50;
            instance.setContentView(R.layout.answer_correct);}
        else{
            instance.setContentView(R.layout.answer_wrong);}
    }
    private void answerzwei() {
        if(randomnumber==2){
            coins = +50;
            instance.setContentView(R.layout.answer_correct);}
        else{
            instance.setContentView(R.layout.answer_wrong);}
    }
    private void answerdrei(){
        if (randomnumber == 1){
            coins=+50;
            instance.setContentView(R.layout.answer_correct);}
        else{
            instance.setContentView(R.layout.answer_wrong);}
    }
    private void answervier(){
        if (randomnumber == 0){
            coins=+50;
            instance.setContentView(R.layout.answer_correct);}
        else{
            instance.setContentView(R.layout.answer_wrong);}

    }

}
