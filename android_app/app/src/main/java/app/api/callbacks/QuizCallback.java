package app.api.callbacks;

import app.api.quiz.GenericQuiz;

public interface QuizCallback {

    public void execute(GenericQuiz quiz);
}
