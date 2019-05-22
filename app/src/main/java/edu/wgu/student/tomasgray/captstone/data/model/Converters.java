package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Converters
{
    private static final String LOG_TAG = "Converters";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static Gson gson = new Gson();

    // TODO: DELETE THESE; convert to LocalDate
    // Dates
    // ---------------------------------------------------------------------------------
    @TypeConverter
    public static Date dateFromString(Long date)
    {
        Log.i(LOG_TAG, "Converting Long to Date: " + date);
        return (date == null) ? null : new Date(date);
    }

    @TypeConverter
    public static Long dateToString(Date date) {
        Log.i(LOG_TAG, "Converting Date to Long: " + date.getTime());
        return (date == null) ? null : date.getTime();
    }

    // LocalDate
    // ---------------------------------------------------------------------------------
    // TODO: Get LocalDate serialization working!!!
    @TypeConverter
    public static LocalDate localDateFromString(String date) {
        Log.i(LOG_TAG, "Converting String to LocalDate: " + date);
        return (date == null) ? null : LocalDate.parse(date);
    }
    @TypeConverter
    public static String localDateToString(LocalDate date) {
        Log.i(LOG_TAG, "Converting LocalDate to String: " + date.toString());
        return (date == null) ? null : date.toString();
    }


    // UUID
    // ---------------------------------------------------------------------------------
    @TypeConverter
    public static UUID uuidFromString(String uuid) {
        return (uuid == null) ? null : UUID.fromString(uuid);
    }
    @TypeConverter
    public static String uuidToString(UUID uuid) {
        return (uuid == null) ? null : uuid.toString();
    }

    // Course.Status
    // --------------------------------------------------------------------------------
    @TypeConverter
    public static Course.Status statusFromString(String status) {
        Log.i(LOG_TAG, "Converting to Course.Status from String: " + status);
        return (status == null) ? null : Course.Status.valueOf(status);
    }
    @TypeConverter
    public static String statusToString(Course.Status status) {
        return (status == null) ? null : status.name();
    }

    // Assessment.AssessmentType
    // -------------------------------------------------------------------------------
    @TypeConverter
    public static Assessment.AssessmentType typeFromString(String type) {
        return (type == null) ? null : Assessment.AssessmentType.valueOf(type);
    }
    @TypeConverter
    public static String typeToString(Assessment.AssessmentType type) {
        return (type == null) ? null : type.name();
    }

    // Assessment.AssessmentItem
    // -------------------------------------------------------------------------------
    @TypeConverter
    public static Assessment.AssessmentItem itemFromString(String item) {
        Log.i(LOG_TAG, "Converting String to AssessmentItem: " + item);

        return new Assessment.AssessmentItem();
    }
    @TypeConverter
    public static String itemToString(Assessment.AssessmentItem item) {
        // Convert object to JSON String
        Log.i(LOG_TAG, "Converting AssessmentItem to String: " + item);

        return gson.toJson(item);
    }
    @TypeConverter
    public static List<Assessment.AssessmentItem> itemListFromString(String itemList) {
        Log.i(LOG_TAG, "Converting String to List of AssessmentItems: " + itemList);

        Type listType = new TypeToken<ArrayList<Assessment.AssessmentItem>>(){}.getType();
        return gson.fromJson(itemList, listType);
    }
    @TypeConverter
    public static String itemListToString(List<Assessment.AssessmentItem> items) {
        Log.i(LOG_TAG, "Converting List of AssessmentItems to JSON string: " + items);
        return gson.toJson(items);
    }
}
