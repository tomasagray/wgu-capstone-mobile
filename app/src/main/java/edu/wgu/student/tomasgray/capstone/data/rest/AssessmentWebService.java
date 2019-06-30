package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Assessment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AssessmentWebService
{
    // Fetch all assessments from remote server
    @GET("assessments")
    Call<List<Assessment>> getAllAssessments();

    @GET("courses/{course_id}/assessments/")
    Call<List<Assessment>> getAssessmentsForCourse(
            @Header("Authorization") String authKey,
            @Path("course_id") UUID courseId
    );

    // Fetch specific assessment
    @GET("assessments/{assessmentId}")
    Call<Assessment> getAssessment(@Path("assessmentId")UUID assessmentId);
}
