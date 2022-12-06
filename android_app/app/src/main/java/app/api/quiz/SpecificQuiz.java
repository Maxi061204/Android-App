package app.api.quiz;

import org.json.JSONObject;

import app.api.callbacks.SpecificQuizCallback;
import app.api.codes.ApiCodes;
import app.api.codes.HttpCodes;
import app.api.requests.quiz.GetSpecificQuizRequest;
import app.api.state.StateManager;

public class SpecificQuiz {


    public static void vonPlatzName(String name, SpecificQuizCallback callback){
        GetSpecificQuizRequest request = new GetSpecificQuizRequest(name, StateManager.getInstance().getToken(), response -> {
            if (!response.couldConnect() || response.getHttpCode() != HttpCodes.OK){
                System.err.println("Response is either null or does not have the 200 code!\nResponse: " + (response.getResponse() != null ? response.getResponse().code() : "null"));
                callback.execute(null);
                return;
            }

            int code = response.getJson().optInt("code", -1);

            ApiCodes apiCode = ApiCodes.getFromCode(code);

            if (apiCode == null){
                System.err.println("Invalid api code after requesting generic Quiz: " + code);
                callback.execute(null);
                return;
            }

            if (apiCode == ApiCodes.SUCCESS){
                JSONObject quizObject;

                quizObject = response.getJson().optJSONObject("quiz");
            }
        });

        request.doRequest();
    }
}
