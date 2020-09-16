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
