package app.api.callbacks;

import app.api.generics.ApiResponse;

public interface RequestCallback {

    void execute(ApiResponse response);
}
