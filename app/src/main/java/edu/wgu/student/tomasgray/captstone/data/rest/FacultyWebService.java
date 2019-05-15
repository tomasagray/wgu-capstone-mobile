package edu.wgu.student.tomasgray.captstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Faculty;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FacultyWebService
{
    // Get all faculty members
    @GET("faculty")
    Call<List<Faculty>> getAllFaculty();

    // Get a specific faculty member
    @GET("faculty/{faculty_id}")
    Call<Faculty> getFaculty(@Path("faculty_id") UUID faculty_id);
}
