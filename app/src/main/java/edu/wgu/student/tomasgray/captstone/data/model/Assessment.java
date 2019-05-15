package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Assessment
{

    public enum AssessmentType {
        OBJECTIVE,
        PERFORMANCE
    }

    @PrimaryKey
    @NonNull
    @SerializedName("assessment_id")
    private final UUID id;
    private String title;
    private AssessmentType type;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;


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
    public Assessment(UUID id, String title, LocalDate startDate) {
        this(id, title);
        this.startDate = startDate;
    }
    // Used by Room
    public Assessment(UUID id, String title, LocalDate startDate, LocalDate endDate) {
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
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
