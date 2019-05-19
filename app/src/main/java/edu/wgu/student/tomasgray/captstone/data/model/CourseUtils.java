package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CourseUtils
{
    private static final String LOG_TAG = "CourseUtils";


    private static SimpleDateFormat shortFormatter
            = new SimpleDateFormat("MM/dd", Locale.US);

    public static String getShortDate(@NonNull Date date) {
        return shortFormatter.format(date);
    }

    public static String getCreditString(@NonNull Course course) {
        return course.getCredits() + " credits";
    }

    public static int getDaysLeft(@NonNull Course course)
    {

        // Compare to current date
        long diff =
                // End date
                course.getEndDate().getTime()
                // vs.     now
              - new Date().getTime();
        return
                (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    public static String getDaysLeftString(@NonNull Course course) {
        return getDaysLeft(course) + "";
    }

    public static int getCourseLength(@NonNull Course course)
    {
        long courseLength =
                    course.getEndDate().getTime()
                    - course.getStartDate().getTime();
        return
                (int) TimeUnit.DAYS.convert(courseLength, TimeUnit.MILLISECONDS);
    }

    public static int getPercentComplete(@NonNull Course course)
    {

        float daysLeft = (float)getDaysLeft(course);
        float courseLength = (float)getCourseLength(course);
        float percentComplete = 1.0f - (daysLeft / courseLength);
        int percent = (percentComplete >= 1.0f) ? 100 : (int)(percentComplete*100);

        Log.i(LOG_TAG,"Returning percent: " + percent);
        // Return as percent
        return percent;
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
