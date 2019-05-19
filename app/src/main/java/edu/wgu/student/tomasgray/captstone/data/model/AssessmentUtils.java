package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentUtils
{
    private static final SimpleDateFormat longFormatter
            = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public static Topic convertToTopic(Assessment assessment)
    {
        String flag = getTopicFlag(assessment);
        String start = getLongDate(assessment.getStartDate());
        return new Topic(
                flag,
                assessment.getTitle(),
                start
        );
    }

    @NonNull
    public static String getTopicFlag(@NonNull Assessment assessment) {
        return assessment.getType().name().substring(0,1);
    }

    public static String getLongDate(Date date) {
        return longFormatter.format(date);
    }

}
