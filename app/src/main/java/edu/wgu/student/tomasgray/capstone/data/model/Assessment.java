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
import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

@Entity(primaryKeys = {"id", "courseId"})
public class Assessment
{
    private static final String LOG_TAG = "AssessmentClass";

    public enum AssessmentType {
        @SerializedName("objective")
        OBJECTIVE,
        @SerializedName("performance")
        PERFORMANCE
    }

    public static class AssessmentItem
    {
        private String title;
        private String description;
        private String competent;
        @SerializedName("semi_competent")
        private String approaching;
        private String incompetent;

        public AssessmentItem(String data) {
            Log.i(LOG_TAG, "Constructing new assessment item: " + data);
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getCompetent() {
            return competent;
        }
        public void setCompetent(String competent) {
            this.competent = competent;
        }
        public String getApproaching() {
            return approaching;
        }
        public void setApproaching(String approaching) {
            this.approaching = approaching;
        }
        public String getIncompetent() {
            return incompetent;
        }
        public void setIncompetent(String incompetent) {
            this.incompetent = incompetent;
        }
    }


    @NonNull
    @SerializedName("assessment_id")
    private final UUID id;
    @NonNull
    @SerializedName("course_id")
    private UUID courseId;
    private String title;
    private AssessmentType type;
    @SerializedName("items")
    private List<AssessmentItem> assessmentItems;


    // Constructors
    @Ignore
    public Assessment(@NonNull UUID id, @NonNull UUID courseId) {
        this.id = id;
        this.courseId = courseId;
    }
    // Used by Room
    public Assessment(@NonNull UUID id, @NonNull UUID courseId, String title) {
        this(id, courseId);
        this.title = title;
    }

    // Getters & Setters
    // --------------------------------------------------------------
    @NonNull
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    @NonNull
    public UUID getCourseId() {
        return courseId;
    }
    public void setCourseId(@NonNull UUID courseId) {
        this.courseId = courseId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public AssessmentType getType() {
        return type;
    }
    public void setType(AssessmentType type) {
        this.type = type;
    }
    public List<AssessmentItem> getAssessmentItems() {
        return this.assessmentItems;
    }
    public void setAssessmentItems(List<AssessmentItem> items) {
        this.assessmentItems = items;
    }


    @NonNull
    public String toString() {
        return
                "ID: " + this.id + "\n"
                +"Course ID: " + getCourseId() + "\n"
                +"Title: " + this.title + "\n"
                +"Item count: " + getAssessmentItems().size();
    }
}
