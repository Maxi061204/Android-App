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

    private TextView frage, zeit;

    private Button eins, zwei, drei, vier, back;

    public QuizScreen() {
        instance = MainActivity.getInstance();

        instance.runOnUiThread(() -> instance.setContentView(R.layout.quiz_screen));

        run();
    }


    private void run() {
        // Hier ist die Api-Anfrage in der GenericQuiz Klasse verpackt. Hier muss nur noch geprüft werden ob die Anfrage erfolgreich war.
        GenericQuiz.getRandomQuiz(quiz -> {
            if (quiz == null) {
                Utils.showErrorMessage(instance, "Ein Fehler ist aufgetreten, als ein Quiz vom Server abgefraget werden sollte!", "");
                return;
            }

            instance.runOnUiThread(() -> {
                frage = instance.findViewById(R.id.tobi_frage);
                zeit = instance.findViewById(R.id.tobi_zeit);

                eins = instance.findViewById(R.id.tobi_button_eins);
                zwei = instance.findViewById(R.id.tobi_button_zwei);
                drei = instance.findViewById(R.id.tobi_button_drei);
                vier = instance.findViewById(R.id.tobi_button_vier);
                back = instance.findViewById(R.id.back);

                frage.setText(quiz.getQuestion());

                Random number = new Random();
                int randomnumber = number.nextInt(4);//je nach Zahl werden die Texte anders zugeordnet
                if (randomnumber == 0) {
                    eins.setText(quiz.getWrongAnswers()[0]);
                    zwei.setText(quiz.getWrongAnswers()[1]);
                    drei.setText(quiz.getWrongAnswers()[2]);
                    vier.setText(quiz.getCorrectAnswer());
                } else {
                    if (randomnumber == 1) {
                        eins.setText(quiz.getWrongAnswers()[1]);
                        zwei.setText(quiz.getWrongAnswers()[2]);
                        drei.setText(quiz.getCorrectAnswer());
                    } else {
                        if (randomnumber == 2) {
                            eins.setText(quiz.getWrongAnswers()[2]);
                            zwei.setText(quiz.getCorrectAnswer());
                        } else {
                            eins.setText(quiz.getCorrectAnswer());
                            zwei.setText(quiz.getWrongAnswers()[2]);
                        }
                        drei.setText(quiz.getWrongAnswers()[1]);
                    }
                    vier.setText(quiz.getWrongAnswers()[0]);
                }

                back.setOnClickListener(event -> {
                    new MapScreen();
                });

                //jeder button gibt die Zahl mit, die der Zufallszahl entspricht, bei der dieser die richtige Antwort zugeordmet bekommen hat
                eins.setOnClickListener(event -> {
                    double damitdasProgrammzufriedenist = 2;
                    if (randomnumber == 3) {
                        new FalseQuizAnswerScreen();
                    } else {
                        //Toast.makeText(this.instance.getApplicationContext(), "Falsche Antwort! Neue Frage", Toast.LENGTH_SHORT).show(); (alte Lösung)
                        new RightQuizAnswerScreen();
                    }
                });
                zwei.setOnClickListener(event -> {
                    if (randomnumber == 2) {
                        double damitdasProgrammzufriedenist = 2;
                        new FalseQuizAnswerScreen();
                    } else {
                        //Toast.makeText(this.instance.getApplicationContext(), "Falsche Antwort! Neue Frage", Toast.LENGTH_SHORT).show(); (alte Lösung)
                        new RightQuizAnswerScreen();
                    }
                });
                drei.setOnClickListener(event -> {
                    if (randomnumber == 1) {
                        double damitdasProgrammzufriedenist = 2;
                        new FalseQuizAnswerScreen();
                    } else {
                        //Toast.makeText(this.instance.getApplicationContext(), "Falsche Antwort! Neue Frage", Toast.LENGTH_SHORT).show(); (alte Lösung)
                        new RightQuizAnswerScreen();
                    }
                });
                vier.setOnClickListener(event -> {
                    if (randomnumber == 0) {
                        double damitdasProgrammzufriedenist = 2;
                        new FalseQuizAnswerScreen();
                    } else {
                        //Toast.makeText(this.instance.getApplicationContext(), "Falsche Antwort! Neue Frage", Toast.LENGTH_SHORT).show(); (alte Lösung)
                        new RightQuizAnswerScreen();
                    }
                });

            });

        });


        //Timer
        double startzeit = (System.currentTimeMillis());
         double damitdasProgrammzufriedenist = 0;
        while (1 > damitdasProgrammzufriedenist) {
            double vergangenezeit = System.currentTimeMillis() - startzeit;
            if (vergangenezeit < 1000) {
                zeit.setText("10");
            } else {
                if (vergangenezeit < 2000) {
                    zeit.setText("9");
                } else {
                    if (vergangenezeit < 3000) {
                        zeit.setText("8");
                    } else {
                        if (vergangenezeit < 4000) {
                            zeit.setText("7");
                        } else {
                            if (vergangenezeit < 5000) {
                                zeit.setText("6");
                            } else {
                                if (vergangenezeit < 6000) {
                                    zeit.setText("5");
                                } else {
                                    if (vergangenezeit < 7000) {
                                        zeit.setText("4");
                                    } else {
                                        if (vergangenezeit < 8000) {
                                            zeit.setText("3");
                                        } else {
                                            if (vergangenezeit < 9000) {
                                                zeit.setText("2");
                                            } else {
                                                if (vergangenezeit < 10000) {
                                                    zeit.setText("1");
                                                } else {
                                                    damitdasProgrammzufriedenist = 2;
                                                    new FalseQuizAnswerScreen();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ;
    }
}



