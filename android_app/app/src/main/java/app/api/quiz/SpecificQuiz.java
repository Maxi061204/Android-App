package app.api.quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.MainActivity;
import app.api.callbacks.SpecificQuizCallback;
import app.api.callbacks.SpecificQuizNameCallback;
import app.api.codes.ApiCodes;
import app.api.codes.HttpCodes;
import app.api.requests.quiz.GetAllSpecificQuizRequests;
import app.api.requests.quiz.GetSpecificQuizRequest;
import app.api.state.StateManager;
import app.ui.utils.Utils;

public class SpecificQuiz {
    private SubSpecificQuiz[] subQuizzes;
    private String locationName;

    public SpecificQuiz(String locationName, SubSpecificQuiz[] subQuizzes){
        this.subQuizzes = subQuizzes;
        this.locationName = locationName;
    }

    public SubSpecificQuiz[] getSubQuizzes() {
        return subQuizzes;
    }

    public String getLocationName() {
        return locationName;
    }

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
                System.err.println("Invalid api code after requesting specific Quiz: " + code);
                callback.execute(null);
                return;
            }

            if (apiCode == ApiCodes.SUCCESS){
                JSONObject quizObject;

                quizObject = response.getJson().optJSONObject("quiz");

                String ortName = quizObject.optString("name");

                JSONArray parts;

                try {
                    parts = quizObject.getJSONArray("parts");
                } catch (JSONException e) {
                    Utils.showErrorMessage(MainActivity.getInstance(), "Netzwerkfehler!", "Die Quizze konntent nicht ausgelesen werden!");
                    callback.execute(null);
                    return;
                }

                SubSpecificQuiz[] quizze = new SubSpecificQuiz[parts.length()];

                for (int i = 0; i < parts.length(); i++){
                    JSONObject obj;
                    try {
                        obj = parts.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        continue;
                    }

                    SubSpecificQuiz quiz = new SubSpecificQuiz(obj.optString("beschreibung"), obj.optString("name"), obj.optString("koordinaten"));

                    quizze[i] = quiz;
                }

                callback.execute(new SpecificQuiz(ortName, quizze));
            }
        });

        request.doRequest();
    }

    public static void alleQuizze(SpecificQuizNameCallback callback){
        GetAllSpecificQuizRequests request = new GetAllSpecificQuizRequests(StateManager.getInstance().getToken(), response -> {
            if (!response.couldConnect() || response.getHttpCode() != HttpCodes.OK){
                System.err.println("Response is either null or does not have the 200 code!\nResponse: " + (response.getResponse() != null ? response.getResponse().code() : "null"));
                callback.execute(null);
                return;
            }

            int code = response.getJson().optInt("code", -1);

            ApiCodes apiCode = ApiCodes.getFromCode(code);

            if (apiCode == null){
                System.err.println("Invalid api code after requesting specific Quiz: " + code);
                callback.execute(null);
                return;
            }

            if (apiCode == ApiCodes.SUCCESS){
                JSONArray array;

                array = response.getJson().optJSONArray("data");

                if (array == null){
                    Utils.showErrorMessage(MainActivity.getInstance(), "Netzwerkfehler", "Konnte das Array nicht auslesen!");
                    callback.execute(null);
                    return;
                }

                SpecifcQuizName[] names = new SpecifcQuizName[array.length()];

                for (int i=0; i < array.length(); i++){
                    JSONObject obj;

                    try {
                        obj = array.getJSONObject(i);
                    } catch (JSONException e) {
                        Utils.showErrorMessage(MainActivity.getInstance(), "Netzwerkfehler", "Konnte den JSON-Body nicht auslesen!");
                        callback.execute(null);
                        return;
                    }

                    names[i] = new SpecifcQuizName(obj.optString("name"), obj.optString("koords"));
                }

                callback.execute(names);
            } else {
                Utils.showErrorMessage(MainActivity.getInstance(), "Netzwerkfehler", "Api code ist nicht ok: " + apiCode);
                callback.execute(null);
                return;
            }
        });

        request.doRequest();
    }
}
