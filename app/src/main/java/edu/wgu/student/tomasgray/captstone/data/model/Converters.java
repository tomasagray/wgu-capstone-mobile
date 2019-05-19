package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Converters
{
    private static final String LOG_TAG = "Converters";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
}
