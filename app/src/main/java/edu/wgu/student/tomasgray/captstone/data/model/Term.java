package edu.wgu.student.tomasgray.captstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Entity
public class Term
{
    @PrimaryKey
    @NonNull
    private final UUID termId;
    private String title;
    private Date startDate;
    private Date endDate;


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
    public Term(UUID termId, String title, Date start) {
        this(termId, title);
        this.startDate = start;
    }
    // Used by Room
    public Term(UUID termId, String title, Date startDate, Date endDate) {
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
    // Computer difference between now and the end of the term
    public int getDaysLeft() {
        long diff = Math.abs(this.endDate.getTime() - new Date().getTime());
        return
                (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
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
