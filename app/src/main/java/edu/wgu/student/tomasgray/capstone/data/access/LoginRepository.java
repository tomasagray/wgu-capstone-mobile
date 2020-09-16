/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.capstone.data.model.Result;
import edu.wgu.student.tomasgray.capstone.data.rest.Authorization;
import edu.wgu.student.tomasgray.capstone.data.rest.LoginWebService;
import edu.wgu.student.tomasgray.capstone.data.rest.RestClient;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository
{
    private static final String LOG_TAG = "LoginRepo";


    private static volatile LoginRepository instance;
    private LoginWebService webService;
    private Executor executor;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private MutableLiveData<Result<Authorization>> loginResult = new MutableLiveData<>();

    // private constructor : singleton access
    // TODO: Rewrite so more like other Repos
    private LoginRepository() {
        webService = RestClient.getInstance().create(LoginWebService.class);
        executor = Executors.newCachedThreadPool();
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loginResult != null;
    }

    public void logout() {
        loginResult.setValue(null);
    }

    public MutableLiveData<Result<Authorization>> getLoginResult() {
        return this.loginResult;
    }

    public void login(String username, String password)
    {
        executor.execute(() -> {
            try {
                String authKey = Authorization.getAuthKey(username, password);
                Log.i(LOG_TAG, "Logging in with auth key: " + authKey);

                Response<Authorization> response
                        = webService
                            .login(authKey)
                            .execute();

                Log.i(LOG_TAG, "Response: " + response.toString());

                if (response.isSuccessful()) {
                    Authorization authorization = response.body();
                    Log.i(
                            LOG_TAG,
                            "Login was successful: (u): "
                                + authorization.getUserId()
                                + ", (TOKEN): "
                                + authorization.getToken()
                    );

                    this.loginResult.postValue(
                            new Result.Success<Authorization>(authorization)
                    );
                    // TODO: Redundant; eliminate?
//                    App.setAuthorization(authorization);

                } else {
                    Log.i(LOG_TAG, "Login failed: " + response);
                    // TODO: Handle login failure
                    this.loginResult.postValue(
                            new Result.Error(new IOException("Error logging in"))
                    );
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error logging in: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
