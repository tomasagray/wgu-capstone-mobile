package edu.wgu.student.tomasgray.capstone.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Entity
public class Term
{
    @PrimaryKey
    @NonNull
    @SerializedName("term_id")
    private final UUID termId;
    @SerializedName("student_id")
    private UUID studentId;
    private String title;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;

    @Ignore
    private List<Course> courseList;

    // Constructors
    // -----------------------------------------------------------
    @Ignore
    public Term(@NonNull UUID termId) {
        this.termId = termId;
    }
    @Ignore
    public Term(UUID termId, String title) {
        this(termId);
        this.title = title;
    }
    @Ignore
    public Term(UUID termId, String title, LocalDate start) {
        this(termId, title);
        this.startDate = start;
    }
    // Used by Room
    public Term(UUID termId, UUID studentId, String title, LocalDate startDate, LocalDate endDate) {
        this(termId, title, startDate);
        this.studentId = studentId;
        this.endDate = endDate;
    }

    // Getters & Setters
    // ------------------------------------------------------------
    @NonNull
    public UUID getTermId() {
        return termId;
    }
    public UUID getStudentId() {
        return this.studentId;
    }
    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public List<Course> getCourseList() {
        return this.courseList;
    }
    public void setCourseList(List<Course> courses) {
        this.courseList = courses;
    }

    /**
     * Compute difference between now and the end of the term
     * @return int Number of days remaining in term, based on current date
     */
    public int getDaysLeft() {
        int days = ((int)ChronoUnit.DAYS.between( LocalDate.now(), getEndDate()));
        Log.i("TERM_CLASS",
                    "Now: " + LocalDate.now() + ", " +
                "End date: " + getEndDate() + ", days left: " + days);
        return days;
    }

    @NonNull
    @Override
    public String toString()
    {
        return
                "ID: " + this.termId + "\n"
                +"Title: " + this.title + "\n"
                +"Start: " + this.startDate + "\n"
                +"End: " + this.endDate;
    }
}
