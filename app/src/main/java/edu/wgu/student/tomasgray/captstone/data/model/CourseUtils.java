package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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
        return
                Period
                    .between(LocalDate.now(), course.getEndDate())
                    .getDays();
    }

    public static String getDaysLeftString(@NonNull Course course) {
        return getDaysLeft(course) + "";
    }

    private static int getCourseLength(@NonNull Course course)
    {
        return
                Period
                    .between(course.getStartDate(), course.getEndDate())
                    .getDays();

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
