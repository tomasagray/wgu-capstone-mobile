package edu.wgu.student.tomasgray.capstone.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class CourseUtils
{
    private static final String LOG_TAG = "CourseUtils";

    // TODO: Rethink this class

    private static SimpleDateFormat shortFormatter
            = new SimpleDateFormat("MM/dd", Locale.US);

    public static String getShortDate(@NonNull LocalDate date) {
        return shortFormatter.format(date);
    }

    public static String getCreditString(@NonNull Course course) {
        return course.getCredits() + " credits";
    }

    private static int getDaysLeft(@NonNull Course course)
    {
        int days = ((int)ChronoUnit
                        .DAYS
                        .between( LocalDate.now(), course.getEndDate() ));
        return (days < 0) ? 0 : days;
    }

    public static String getDaysLeftString(@NonNull Course course) {
        return getDaysLeft(course) + "";
    }

    private static int getCourseLength(@NonNull Course course)
    {
        return
                ((int)ChronoUnit
                    .DAYS
                    .between( course.getStartDate(), course.getEndDate() ));
    }

    public static int getPercentComplete(@NonNull Course course)
    {

        int daysLeft = getDaysLeft(course);
        int courseLength = getCourseLength(course);
        Log.i(LOG_TAG, "daysLeft = " + daysLeft + ", courseLength = " + courseLength);
        float pct = daysLeft / (float)courseLength;
        Log.i(LOG_TAG,"Returning percent: " + pct);
        // Return as percent
        return 100 - Math.round(100 * pct);
    }

    public static Topic convertToTopic(@NonNull Course course)
    {
        return new Topic(
                course.getCredits()+"",
                course.getTitle(),
                course.getStatusName()
        );
    }
}
