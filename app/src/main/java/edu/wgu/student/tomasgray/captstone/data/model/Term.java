package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;


@Entity
public class Term
{
    @PrimaryKey
    @NonNull
    private final UUID termId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;


    // Constructors
    // -----------------------------------------------------------
    @Ignore
    public Term(UUID termId) {
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
    public Term(UUID termId, String title, LocalDate startDate, LocalDate endDate) {
        this(termId, title, startDate);
        this.endDate = endDate;
    }

    // Getters & Setters
    // ------------------------------------------------------------
    @NonNull
    public UUID getTermId() {
        return termId;
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
    // Computer difference between now and the end of the term
    public int getDaysLeft() {
        Period period = Period.between(LocalDate.now(), this.endDate);
        return period.getDays();
    }

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
