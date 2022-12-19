package app.api.requests.quiz;

import app.api.generics.ApiRequest;
import app.api.callbacks.RequestCallback;
import app.api.generics.RequestType;

public class GetSpecificQuizRequest extends ApiRequest {

    public GetSpecificQuizRequest(String platzName, String token, RequestCallback callback){
        super();

        super.addHeader("token", token);

        super.setEndpoint("/big_quiz/by_name/" + platzName);
        super.setRequestType(RequestType.GET);
        super.setCallback(callback);
    }
}
