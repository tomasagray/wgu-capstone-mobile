package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Assessment
{
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
        private String competence;
        private String approaching;
        private String incompetence;

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
        public String getCompetence() {
            return competence;
        }
        public void setCompetence(String competence) {
            this.competence = competence;
        }
        public String getApproaching() {
            return approaching;
        }
        public void setApproaching(String approaching) {
            this.approaching = approaching;
        }
        public String getIncompetence() {
            return incompetence;
        }
        public void setIncompetence(String incompetence) {
            this.incompetence = incompetence;
        }
    }

    @PrimaryKey
    @NonNull
    @SerializedName("assessment_id")
    private final UUID id;
    private String title;
    private AssessmentType type;
    private Date startDate;
    private Date endDate;
    @SerializedName("items")
    private List<AssessmentItem> assessmentItems;


    // Constructors
    @Ignore
    public Assessment(UUID id) {
        this.id = id;
    }
    @Ignore
    public Assessment(UUID id, String title) {
        this(id);
        this.title = title;
    }
    @Ignore
    public Assessment(UUID id, String title, Date startDate) {
        this(id, title);
        this.startDate = startDate;
    }
    // Used by Room
    public Assessment(UUID id, String title, Date startDate, Date endDate) {
        this(id, title, startDate);
        this.endDate = endDate;
    }

    // Getters & Setters
    // --------------------------------------------------------------
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public List<AssessmentItem> getAssessmentItems() {
        return this.assessmentItems;
    }
    public void setAssessmentItems(List<AssessmentItem> items) {
        this.assessmentItems = items;
    }

    @Override
    public String toString() {
        return
                "ID: " + this.id + "\n"
                +"Title: " + this.title + "\n"
                +"Start: " + this.startDate + "\n"
                +"End: " + this.endDate;
    }
}
