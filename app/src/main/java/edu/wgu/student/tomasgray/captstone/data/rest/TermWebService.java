package edu.wgu.student.tomasgray.captstone.data.rest;

import java.util.List;

import edu.wgu.student.tomasgray.captstone.data.model.Term;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TermWebService
{
    @GET("terms")
    Call<List<Term>> getAllTerms();

    @GET("terms/{term_id}")
    Call<Term> getTerm(@Path("term_id") String termId);
}
