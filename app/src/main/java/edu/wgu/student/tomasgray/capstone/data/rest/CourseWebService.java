package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Course;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CourseWebService
{
    // Fetch all course data from server
    @GET("courses")
    Call<List<Course>> getAllCourses(
            @Header ("Authorization") String authKey
    );

    @GET("students/{student_id}/terms/{term_id}/courses/")
    Call<List<Course>> getCoursesForTerm(
            @Header("Authorization") String authKey,
            @Path("student_id") UUID studentId,
            @Path("term_id") UUID termId
    );

    // Fetch data for specific course
    @GET("courses/{course}")
    Call<Course> getCourse(@Header ("Authorization") String authKey, @Path("course") UUID courseId);

    // TODO: Delete me
    @GET("courses/{course}")
    Call<ResponseBody> getCourseResponse(@Path("course") String courseId);
}
