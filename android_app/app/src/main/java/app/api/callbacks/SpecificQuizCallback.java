package app.api.callbacks;

import app.api.generics.ApiResponse;
import app.api.quiz.SpecificQuiz;

public interface SpecificQuizCallback {

    void execute(SpecificQuiz quiz);
}
