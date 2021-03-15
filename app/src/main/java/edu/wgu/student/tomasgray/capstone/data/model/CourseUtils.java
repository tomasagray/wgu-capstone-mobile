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

    private static final SimpleDateFormat shortFormatter
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
        return Math.max(days, 0);
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
