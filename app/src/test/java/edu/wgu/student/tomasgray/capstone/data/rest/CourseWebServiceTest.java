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

import androidx.annotation.Nullable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import edu.wgu.student.tomasgray.capstone.data.model.Course;
import retrofit2.Response;

public class CourseWebServiceTest
{
    private static final String LOG_TAG = "CourseWSTest";

    private final UUID testId = UUID.fromString("0d1905ca-62b4-4806-87cc-93a4cb450f26");
    private static final String userId = "39a8b804-3184-4f61-b976-f16e857fb10a";
    private static final String authKey = userId + ":W23LFtYwkj1dR1rl4bhPYxdNmsvJZkZSZ6PP8po/jC4=";
    private static CourseWebService webService;

    @BeforeAll
    static void setup()
    {
        webService
                = RestClient
                .getInstance()
                .create(CourseWebService.class);
    }

    @DisplayName("Get a single course and make sure it's the one we wanted")
    @Test
    void testGetCourse()
    {
        try {
            // Get a course
            Response<Course> response
                    = webService
                    .getCourse(authKey, testId)
                    .execute();
            // Extract response
            Course course = response.body();
            System.out.println("Retrieved course:\n" + course);

            assert course.getId().compareTo(testId) == 0;

        } catch (IOException e ) {
            System.out.println("Failed getting data\n" + e.getMessage());
        }
    }


    @DisplayName("Retrieve all courses at once")
    @ParameterizedTest(name="Testing: {index} | {0}")
    @MethodSource("getAllCourses")
    void testGetsAllValidCourseData(Course course)
    {
        System.out.println("Got course: ");
        System.out.println(course);

        // Test each field is present
        assert course.getTitle() != null && !(course.getTitle().equals(""));
        assert course.getCourseNumber() != null && !(course.getCourseNumber().equals(""));
        assert course.getCredits() >= 3 && course.getCredits() <= 6;
    }


    @Nullable
    private static Stream<Arguments> getAllCourses()
    {
        try {
            Response<List<Course>> response
                    = webService
                        .getAllCourses(authKey)
                        .execute();
            // Extract response
            List<Course> courses = response.body();

            return courses
                    .stream()
                    .map(Arguments::of);

        } catch (IOException | NullPointerException e) {
            System.out.println("Failed retrieving all courses");
            return null;
        }
    }
}
