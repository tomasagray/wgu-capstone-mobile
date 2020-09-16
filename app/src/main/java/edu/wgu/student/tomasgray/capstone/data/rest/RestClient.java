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

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.wgu.student.tomasgray.capstone.data.model.Assessment;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient
{
    private static final String LOG_TAG = "RestClient";


    // Retrofit2 instance
    private static Retrofit retrofit;
    final static DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    // Remote server address
    private static final String BASE_URL
            // Local
            // = "http://10.0.0.5/capstone/api/";
            // Remote
            = "http://24.17.109.70/capstone/api/";

    public static Retrofit getInstance()
    {
        if(retrofit == null)
        {
            retrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(createOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create(createGsonDateFormatter()))
                            .build();
        }

        return retrofit;
    }


    @NonNull
    private static Gson createGsonDateFormatter()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) RestClient::deserialize);
        builder.registerTypeAdapter(Assessment.AssessmentItem.class,
                (JsonDeserializer<Assessment.AssessmentItem>) (json, typeOfT, context) -> {
                    try {
                        // Get JSON object
                        JsonObject obj = json.getAsJsonObject();
                        // Parse JSON into AssessmentItem instance
                        Assessment.AssessmentItem item = new Assessment.AssessmentItem("");
                        item.setTitle(obj.get("title").getAsString());
                        item.setDescription(obj.get("description").getAsString());

                        // Parse rubric items
                        JsonObject rubric = obj.get("rubric").getAsJsonObject();
                        item.setCompetent(rubric.get("competent").getAsString());
                        item.setApproaching(rubric.get("semi_competent").getAsString());
                        item.setIncompetent(rubric.get("incompetent").getAsString());

                        // Return assembled Item
                        return item;

                    } catch (RuntimeException | Error e) {
                        Log.e(
                                LOG_TAG,
                                "Error caught while deserializing AssessmentItem!"
                                        +"\nMessage: " + e.getLocalizedMessage()
                        );
                        e.printStackTrace();
                        // Default
                        return new Assessment.AssessmentItem("");
                    }
                });

        builder.setDateFormat("yyyy-MM-dd");
        return builder.create();
    }

    @NonNull
    private static OkHttpClient createOkHttpClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

//        builder.addInterceptor(new AuthorizationInterceptor());

        return builder.build();

    }

    private static LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDate.parse(json.getAsString());
    }
}
