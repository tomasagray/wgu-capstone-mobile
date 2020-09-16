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
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        indices = {@Index("mentorId")},
        primaryKeys = {"id", "termId"},
        foreignKeys = @ForeignKey(entity = Faculty.class,
                                    parentColumns = "facultyId",
                                    childColumns = "mentorId",
                                    onDelete = CASCADE)
        )
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

    @NonNull
    @SerializedName("course_id")
    private final UUID id;
    @NonNull
    @SerializedName("term_id")
    private UUID termId;
    private String title;
    @SerializedName("course_number")
    private String courseNumber;
    private int credits;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;
    @SerializedName("course_status")
    private Status status;
    @SerializedName("mentor_id")
    private UUID mentorId;
    @SerializedName("mentor_name")
    private String mentorName;
    @SerializedName("mentor_email")
    private String mentorEmail;
    @SerializedName("mentor_phone")
    private String mentorPhone;

    @Ignore
    private Faculty mentor;

    public Faculty getMentor() {
        return mentor;
    }

    public void setMentor(Faculty mentor) {
        this.mentor = mentor;
    }

    // Constructors
    // --------------------------------------------------------
    @Ignore
    public Course(@NonNull UUID id, @NonNull UUID termId) {
        this.id = id;
        this.termId = termId;
    }
    @Ignore
    public Course(UUID id, UUID termId, String title) {
        this(id, termId);
        this.title = title;
    }
    @Ignore
    public Course(UUID id, UUID termId, String title, String courseNumber ) {
        this(id, termId, title);
        this.courseNumber = courseNumber;
    }
    public Course(UUID id, UUID termId, String title, String courseNumber, int credits, LocalDate startDate,
                  LocalDate endDate, Status status)
    {
        this(id, termId, title, courseNumber);
        this.credits = credits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
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
    public void setTermId(@NonNull UUID id) {
        this.termId = id;
    }
    @NonNull
    public UUID getTermId() {
        return this.termId;
    }
    public UUID getMentorId() {
        return this.mentorId;
    }
    public void setMentorId(UUID mentorId) {
        this.mentorId = mentorId;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++
    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }


    // Helpers
    // -----------------------------------------------------------
    @NonNull
    @Override
    public String toString() {
        return
                "ID: " + this.getId() + "\n" +
                "TermID: " + this.getTermId() +"\n" +
                "Mentor Name: " + this.getMentorName() + "\n" +
                "Title: " + this.getTitle() + "\n" +
                "Number: " + this.getCourseNumber() + "\n" +
                "Credits: " + this.getCredits()+ "\n" +
                "Status: " + this.getStatusName() + "\n" +
                "Start: " + this.getStartDate() + "\n" +
                "End: " + this.getEndDate() + "\n";
    }
}
