package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;

public class Converters
{
    private static final String LOG_TAG = "Converters";


    // Dates
    // ---------------------------------------------------------------------------------
    @TypeConverter
    public static LocalDate dateFromString(String date)
    {
        Log.i(LOG_TAG, "Converting String to LocalDate: " + date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        // TODO: HACK! Delete this!
        if("0000-00-00".equals(date)) {
            return LocalDate.now();
        }
        try {
            return
                    (date == null) ? null :
                            format
                                    .parse(date)
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

        } catch (ParseException e) {
            Log.e(
                    LOG_TAG,
                    "Error parsing date: " + date
                    +"Message: " + e.getMessage()
            );

            throw new RuntimeException(e);
        }
    }

    @TypeConverter
    public static String dateToString(LocalDate date) {
        Log.i(LOG_TAG, "Converting Localdate to String: " + date.toString());
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
}
