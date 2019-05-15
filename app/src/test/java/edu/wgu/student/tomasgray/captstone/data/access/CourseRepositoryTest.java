package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import edu.wgu.student.tomasgray.captstone.data.model.Course;

public class CourseRepositoryTest
{
    private static CourseRepository courseRepository;

    @BeforeAll
    static void setup()
    {
        Context context = ApplicationProvider.getApplicationContext();
        courseRepository
                = CourseRepository
                    .getInstance(context);
    }

    @Test
    public void testGetCourse()
    {
        List<Course> courses = courseRepository.getAllCourses().getValue();

        assert courses != null;

        System.out.println("Courses not null! Values:");
        courses.forEach(System.out::println);
    }
}
