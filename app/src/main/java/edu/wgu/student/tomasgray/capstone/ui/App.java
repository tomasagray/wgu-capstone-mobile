package edu.wgu.student.tomasgray.capstone.ui;

import android.app.Application;
import android.util.Log;

import edu.wgu.student.tomasgray.capstone.data.rest.Authorization;

public class App extends Application
{
    private static final String LOG_TAG = "AppGLOBAL";

    private static Authorization authorization;

    public static void setAuthorization(Authorization auth) {
        Log.i(LOG_TAG, "Setting authorization: " + auth);
        authorization = auth;
    }

    public static Authorization getAuthorization() {
        return authorization;
    }

    public boolean hasAuthorization() {
        return (authorization == null);
    }
}