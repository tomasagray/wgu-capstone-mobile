package edu.wgu.student.tomasgray.captstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AssessmentWebService
{
    // Fetch all assessments from remote server
    @GET("assessments")
    Call<List<Assessment>> getAllAssessments();

    // Fetch specific assessment
    @GET("assessments/{assessmentId}")
    Call<Assessment> getAssessment(@Path("assessmentId")UUID assessmentId);
}
