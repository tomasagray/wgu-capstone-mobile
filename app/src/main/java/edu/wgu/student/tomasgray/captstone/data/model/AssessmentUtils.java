package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class AssessmentUtils
{
    private static final SimpleDateFormat longFormatter
            = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public static Topic convertToTopic(Assessment assessment)
    {
        String flag = getTopicFlag(assessment);
        String start
                = assessment
                    .getStartDate()
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        return new Topic(
                flag,
                assessment.getTitle(),
                start
        );
    }

    @NonNull
    private static String getTopicFlag(@NonNull Assessment assessment) {
        return assessment.getType().name().substring(0,1);
    }

}
