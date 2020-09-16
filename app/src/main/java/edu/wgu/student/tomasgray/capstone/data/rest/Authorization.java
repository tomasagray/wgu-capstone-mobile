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

package edu.wgu.student.tomasgray.capstone.data.rest;

import android.util.Base64;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import edu.wgu.student.tomasgray.capstone.data.model.Student;

public class Authorization
{
    private static final String LOG_TAG = "Authorization";
    private static final String CRYPTO_METHOD = "HmacSHA256";

    @SerializedName("user_id")
    private String userId;

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Student student;

    public String getUserId() {
        return userId;
    }
    public String getToken() {
        return token;
    }
    public Student getStudent() {
        return student;
    }

    public String getAuthHeader() {
        return getUserId() + ":" + getToken();
    }


    /**
     * Creates an encoded String to use as an authorization key
     *
     * @param userName
     * @param data
     * @return
     */
    public static String getAuthKey(String userName, String data)
    {
        // Generate HMAC
        try {
            Mac hmac = Mac.getInstance(CRYPTO_METHOD);
            SecretKeySpec keySpec = new SecretKeySpec(data.getBytes(), CRYPTO_METHOD);
            hmac.init(keySpec);

            // Base-64 encode hash
            String authKey =
                    userName + ":" +
                    Base64
                        .encodeToString(
                                hmac.doFinal( userName.getBytes() ),
                                Base64.DEFAULT
                        );

            // Trim the key before returning
            return authKey.trim();

        } catch (GeneralSecurityException e) {
            Log.e(LOG_TAG, "Error: no such algorithm\n\tMessage: " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String toString() {
        return
                "UserID: " + this.getUserId() +
                ", Token: " + this.getToken() +
                "\nStudent: " + this.student;
    }
}
