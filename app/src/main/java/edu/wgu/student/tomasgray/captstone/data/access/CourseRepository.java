package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.rest.CourseWebService;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;
import retrofit2.Response;

public class CourseRepository
{
    private static final String LOG_TAG = "CourseRepository";


    // Singleton
    // --------------------------------------------------------------------------
    private static volatile CourseRepository instance;

    public static CourseRepository getInstance(Context context) {
        if (instance == null) {
            // Get a ref to DAO
            CourseDao courseDao =
                    CourseDatabase
                            .getInstance(context)
                            .courseDao();
            // Define a REST service
            CourseWebService webService =
                    RestClient
                            .getInstance()
                            .create(CourseWebService.class);
            // Create an Executor
            ExecutorService executor = Executors.newCachedThreadPool();

            instance = new CourseRepository(courseDao, webService, executor);
        }

        return instance;
    }

    private final CourseDao courseDao;
    private final CourseWebService webservice;
    private final Executor executor;

    // Constructors
    // --------------------------------------------------------------------------
    private CourseRepository(CourseDao courseDao, CourseWebService webservice, Executor executor) {
        this.courseDao = courseDao;
        this.webservice = webservice;
        this.executor = executor;
    }

    public LiveData<Course> getCourse(UUID courseId) {
        // Ensure data is fresh
        refreshAllCourseData();
        // Return data from local DB
        return courseDao.load(courseId);
    }

    public LiveData<List<Course>> getAllCourses()
    {
        // Ensure data is fresh
        refreshAllCourseData();
        // Return all courses
        return courseDao.loadAll();
    }

    public LiveData<List<Course>> getCurrentCourses(final Date now)
    {
        // Refresh local DB
        refreshAllCourseData();
        // Load currently enrolled courses
        return courseDao.loadCurrent(now);
    }

    public LiveData<List<Course>> getCoursesForTerm(UUID termId)
    {
        // TODO: Implement this method!
        refreshAllCourseData();
        // Load courses associated with this term
        return courseDao.loadCoursesForTerm( termId );
    }

    public void addCourse(final Course course)
    {
        Log.i(LOG_TAG, "Adding course: " + course.toString());

        executor.execute(() -> {
            try {
                Response<Course> response
                        = webservice
                        .addCourse(course)
                        .execute();

                // TODO: Validation
                Log.i(LOG_TAG, "Got response: " + response.body().toString());

                courseDao.save(response.body());

            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Error adding course: " + course.getId() );
                e.printStackTrace();
            }
        });
    }

    public void deleteCourse(final Course course)
    {
        executor.execute(() -> {
            try {
                Response<Course> response
                        = webservice
                        .deleteCourse(course.getId())
                        .execute();

                Log.i(LOG_TAG, "Got response: " + response.body().toString());

            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Error deleting course: " + course.getId().toString() + "\n" + e.getMessage());
            }
        });
    }


    private void refreshAllCourseData()
    {
        executor.execute(() -> {
            // TODO: Prevent if called within X seconds...
//            boolean recentlyRefreshed = courseDao.recentlyRefreshed();

            try {
                Response<List<Course>> response
                        = webservice
                            .getAllCourses()
                            .execute();

                // TODO: Implement validation

                // Extract response
                List<Course> courses = response.body();
                // Save each course to local DB
                courses.forEach(courseDao::save);

            } catch (IOException | RuntimeException e) {
                Log.e(
                        LOG_TAG,
                        "Error fetching all courses"
                        +"\n\tMessage: " + e.getMessage()
                );

                e.printStackTrace();
            }
        });
    }
}
