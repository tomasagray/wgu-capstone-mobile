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