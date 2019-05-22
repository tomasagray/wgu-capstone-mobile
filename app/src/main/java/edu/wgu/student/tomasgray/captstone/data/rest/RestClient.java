package edu.wgu.student.tomasgray.captstone.data.rest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
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

    @NonNull
    private static Gson gsonDateFormatter()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class,
                (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    Log.i(LOG_TAG, "Deserializing Date JSON: " + json.getAsString());

                    try {
                        return format.parse(json.getAsString());

                    } catch (ParseException e) {
                        Log.e(LOG_TAG, "Error parsing JSON: " + json.getAsString());
                        e.printStackTrace();
                        return null;
                    }
                });
        builder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                    Log.i(LOG_TAG, "Deserializing LocalDate JSON: " + json.getAsString());
                    return LocalDate.parse(json.getAsString());
                });
        builder.registerTypeAdapter(Assessment.AssessmentItem.class,
                (JsonDeserializer<Assessment.AssessmentItem>) (json, typeOfT, context) -> {
                    try {
                        Log.i(LOG_TAG, "Deserializing Json to AssessmentItem");

                        // TODO: Implement this!
                        // Get JSON object
                        JsonObject obj = json.getAsJsonObject();
                        // Parse JSON into AssessmentItem instance
                        Assessment.AssessmentItem item = new Assessment.AssessmentItem();
                        item.setTitle(obj.get("title").getAsString());
                        item.setDescription(obj.get("description").getAsString());
                        item.setCompetence(obj.get("competence").getAsString());
                        item.setApproaching(obj.get("approaching").getAsString());
                        item.setIncompetence(obj.get("incompetence").getAsString());
                        // Return assembled Item
                        return item;

                    } catch (RuntimeException | Error e) {
                        Log.e(LOG_TAG, "Error caught while deserializing AssessmentItem!");
                        // Default
                        return new Assessment.AssessmentItem();
                    }
                });

        builder.setDateFormat("yyyy-MM-dd");
        return builder.create();
    }

    public static Retrofit getInstance()
    {
        if(retrofit == null)
        {
            retrofit =
                    new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gsonDateFormatter()))
                        .build();
        }

        return retrofit;
    }
}
