package edu.wgu.student.tomasgray.captstone.data.rest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient
{
    private static final String LOG_TAG = "RestClient";


    // Retrofit2 instance
    private static Retrofit retrofit;
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
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context) throws JsonParseException
            {
                Log.i(LOG_TAG, "Deserializing JSON: " + json.getAsString());

                try {
                    return format.parse(json.getAsString());

                } catch (ParseException e) {
                    Log.e(LOG_TAG, "Error parsing JSON: " + json.getAsString());
                    e.printStackTrace();
                    return null;
                }
            }
        });

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
