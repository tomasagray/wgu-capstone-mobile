package edu.wgu.student.tomasgray.captstone.data.rest;

import org.jetbrains.annotations.Nullable;
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

import edu.wgu.student.tomasgray.captstone.data.model.Course;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CourseWebServiceTest
{
    private static final String LOG_TAG = "CourseWSTest";


    private final UUID testId = UUID.fromString("2bbbcc09-095f-48d7-8f8e-8a82e3aa0f2f");
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
    public void testGetCourse()
    {
        try {
            // Get a course
            Response<Course> response
                    = webService
                    .getCourse(testId.toString())
                    .execute();
            // Extract response
            Course course = response.body();
            System.out.println("Retrieved course:\n" + course);

            assert course.getId().compareTo(testId) == 0;

        } catch (IOException e ) {
            System.out.println("Failed getting data\n" + e.getMessage());
        }
    }

    @Test
    public void testResponse()
    {
        try {
            Response<ResponseBody> response = webService
                    .getCourseResponse(testId.toString())
                    .execute();

            System.out.println(response.body());

        } catch (IOException e) {
            System.out.println("Error getting all courseResponse");
            e.printStackTrace();
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
        assert course.getId() != null;
        assert course.getTitle() != null;
        assert course.getCourseNumber() != null;
        assert course.getCredits() != 0;
        assert course.getStartDate() != null;
        assert course.getEndDate() != null;
        assert course.getStatus() != null;
    }


    @Nullable
    private static Stream<Arguments> getAllCourses()
    {
        try {
            Response<List<Course>> response
                    = webService
                        .getAllCourses()
                        .execute();
            // Extract response
            List<Course> courses = response.body();
            courses.forEach((c) -> {
                System.out.println(c);
            });

            return courses
                    .stream()
                    .map(Arguments::of);

        } catch (IOException | NullPointerException e) {
            System.out.println("Failed retrieving all courses");
            return null;
        }
    }
}
