package app.api.requests.quiz;

import app.api.callbacks.RequestCallback;

public class GetAllSpecificQuizRequests extends GetSpecificQuizRequest {

    public GetAllSpecificQuizRequests(String token, RequestCallback callback) {
        super(null, token, callback);

        super.setEndpoint("/big_quiz/all/");
    }
}
