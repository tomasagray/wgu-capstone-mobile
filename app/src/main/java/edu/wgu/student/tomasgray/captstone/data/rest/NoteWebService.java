package edu.wgu.student.tomasgray.captstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Note;
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
//    @FormUrlEncoded
    Call<Note> saveNote(@Body Note note);

    // Update note
    @PATCH("notes/{noteId}/")
    Call<ResponseBody> updateNote(@Path("noteId") UUID noteId, @Body Note note);
}
