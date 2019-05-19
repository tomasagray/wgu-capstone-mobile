package edu.wgu.student.tomasgray.captstone.data.rest;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Course;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CourseWebService
{
    // Fetch all course data from server
    @GET("courses")
    Call<List<Course>> getAllCourses();

    // Fetch data for specific course
    @GET("courses/{course}")
    Call<Course> getCourse(@Path("course") String courseId);

    // TODO: Delete me
    @GET("courses/{course}")
    Call<ResponseBody> getCourseResponse(@Path("course") String courseId);

    @POST("courses")
    @FormUrlEncoded
    Call<Course> addCourse(@Field("course") Course course);

    @DELETE("courses/{course_id}")
    Call<Course> deleteCourse(@Path("course_id") UUID courseId);
}
