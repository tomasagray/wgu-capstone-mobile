package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Student;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface StudentWebService
{
    @GET("students/{student_id}")
    Call<Student> loadStudentData(
            @Header("Authorization") String authKey,
            @Path("student_id")UUID studentId
    );
}
