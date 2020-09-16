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

package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Note;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteWebService
{
    // Fetch all notes from remote server
    @GET("notes")
    Call<List<Note>> getAllNotes();

    // Fetch specific note
    @GET("notes/{noteId}")
    Call<Note> getNote(@Path("noteId")UUID noteId);

    // Add a new note
    @POST("notes")
    Call<Note> saveNote(@Body Note note);

    // Update note
    @PATCH("notes/{noteId}/")
    Call<ResponseBody> updateNote(@Path("noteId") UUID noteId, @Body Note note);
}
