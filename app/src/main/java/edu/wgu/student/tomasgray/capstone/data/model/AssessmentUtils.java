package edu.wgu.student.tomasgray.capstone.data.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AssessmentUtils
{
    private static final SimpleDateFormat longFormatter
            = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public static Topic convertToTopic(Assessment assessment)
    {
        String flag = getTopicFlag(assessment);
        return new Topic(
                flag,
                assessment.getTitle(),
                assessment.getType().name()
        );
    }

    @NonNull
    private static String getTopicFlag(@NonNull Assessment assessment) {
        return assessment.getType().name().substring(0,1);
    }

}
