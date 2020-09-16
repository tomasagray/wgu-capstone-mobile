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
