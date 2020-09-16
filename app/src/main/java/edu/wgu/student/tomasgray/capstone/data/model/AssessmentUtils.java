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
