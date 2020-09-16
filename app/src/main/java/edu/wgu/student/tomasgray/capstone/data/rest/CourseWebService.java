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
