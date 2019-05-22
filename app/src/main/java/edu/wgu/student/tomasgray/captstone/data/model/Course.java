package edu.wgu.student.tomasgray.captstone.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Course
{
    public enum Status {
        @SerializedName("in_progress")
        IN_PROGRESS,
        @SerializedName("completed")
        COMPLETED,
        @SerializedName("dropped")
        DROPPED,
        @SerializedName("planned")
        PLANNED
    }

    @PrimaryKey
    @NonNull
    @SerializedName("course_id")
    private final UUID id;
    private String title;
    private String courseNumber;
    private int credits;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private UUID termId;

    // Constructors
    // --------------------------------------------------------
    @Ignore
    public Course(@NonNull UUID id) {
        this.id = id;
    }
    @Ignore
    public Course(UUID id, String title) {
        this(id);
        this.title = title;
    }
    @Ignore
    public Course(UUID id, String title, String courseNumber ) {
        this(id, title);
        this.courseNumber = courseNumber;
    }
    public Course(UUID id, String title, String courseNumber, int credits, LocalDate startDate,
                  LocalDate endDate, Status status)
    {
        this(id, title, courseNumber);
        this.credits = credits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

        Log.i("CourseRepoTyped", "id: " + id + ", start: " + startDate.toString());
    }


    // Getters & Setters
    // --------------------------------------------------------
    @NonNull
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
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
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getStatusName() {
        return
                (this.status == null) ? "null" : this.status.name();
    }
    public void setTermId(UUID id) {
        this.termId = id;
    }
    public UUID getTermId() {
        return this.termId;
    }


    // Helpers
    // -----------------------------------------------------------
    @Override
    public String toString() {
        return
                "ID: " + this.getId() + "\n" +
                        "Title: " + this.getTitle() + "\n" +
                        "Number: " + this.getCourseNumber() + "\n" +
                        "Credits: " + this.getCredits()+ "\n" +
                        "Status: " + this.getStatusName() + "\n" +
                        "Start: " + this.getStartDate() + "\n" +
                        "End: " + this.getEndDate() + "\n";
    }
}
