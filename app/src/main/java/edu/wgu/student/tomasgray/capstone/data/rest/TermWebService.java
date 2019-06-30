package edu.wgu.student.tomasgray.capstone.data.rest;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.model.Term;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TermWebService
{
    @GET("students/{student_id}/terms/")
    Call<List<Term>> getAllTerms(
            @Header("Authorization") String token,
            @Path("student_id") String studentId
    );

    @GET("students/{students_id}/terms/{term_id}")
    Call<Term> getTerm(
            @Header("Authorization") String token,
            @Path("student_id") String studentId,
            @Path("term_id") String termId
    );
}
