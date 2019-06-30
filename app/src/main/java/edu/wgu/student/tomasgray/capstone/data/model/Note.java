package edu.wgu.student.tomasgray.capstone.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Note
{
    // Fields
    @PrimaryKey
    @NonNull
    @SerializedName("note_id")
    private final UUID noteId;
    @SerializedName("text")
    private String noteText;
    private LocalDate updateDate;

    public Note(@NonNull UUID noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public UUID getNoteId() {
        return noteId;
    }
    public String getNoteText() {
        return noteText;
    }
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
    public LocalDate getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(LocalDate date) {
        this.updateDate = date;
    }

    @NonNull
    @Override
    public String toString(){
        return
                "Note ID: " + getNoteId() + "\n" +
                        "Text: " + getNoteText() + "\n" +
                        "Last Update: " + getUpdateDate().toString();
    }
}
