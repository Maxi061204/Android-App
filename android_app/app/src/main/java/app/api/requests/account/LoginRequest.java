package app.api.requests.account;

import org.json.JSONException;
import org.json.JSONObject;

import app.api.generics.ApiRequest;
import app.api.callbacks.RequestCallback;
import app.api.generics.RequestType;

public class LoginRequest extends ApiRequest {

    public LoginRequest(String username, String password, RequestCallback callback){
        JSONObject object = new JSONObject();

        try {
            object.put("username", username);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.setRequestType(RequestType.POST);
        super.setEndpoint("/accounts/login");
        super.setBody(object.toString());
        super.setCallback(callback);
    }
}
