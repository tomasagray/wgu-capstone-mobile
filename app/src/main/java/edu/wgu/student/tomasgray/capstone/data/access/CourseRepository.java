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

package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;
import edu.wgu.student.tomasgray.capstone.data.rest.CourseWebService;
import edu.wgu.student.tomasgray.capstone.data.rest.RestClient;
import edu.wgu.student.tomasgray.capstone.ui.App;
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
                        Database
                            .getInstance(context)
                            .courseDao();

            // For faculty data access
            FacultyRepository facultyRepo = FacultyRepository.getInstance(context);

            // Define a REST service
            CourseWebService webService =
                    RestClient
                            .getInstance()
                            .create(CourseWebService.class);
            // Create an Executor
            ExecutorService executor = Executors.newCachedThreadPool();

            instance = new CourseRepository(courseDao, facultyRepo, webService, executor);
        }

        return instance;
    }

    private final CourseDao courseDao;
    private final FacultyRepository facultyRepo;
    private final CourseWebService webservice;
    private final Executor executor;

    // Constructors
    // --------------------------------------------------------------------------
    private CourseRepository(CourseDao courseDao, FacultyRepository facultyRepo, CourseWebService webservice, Executor executor) {
        this.courseDao = courseDao;
        this.facultyRepo = facultyRepo;
        this.webservice = webservice;
        this.executor = executor;
    }

    public LiveData<Course> getCourse(UUID courseId) {
        // Return data from local DB
        LiveData<Course> liveData = courseDao.load(courseId);
        liveData = Transformations.switchMap(liveData, inputCourse -> {
            Log.i(LOG_TAG, "coursedeets InputCourse = " + inputCourse);
            if(inputCourse == null)
                return null;
            LiveData<Faculty> mentorLiveData = facultyRepo.getFacultyMember(inputCourse.getMentorId());
            return Transformations.map(mentorLiveData, input -> {
                inputCourse.setMentor(input);
                return inputCourse;
            });
        });

        return liveData;
    }


    public LiveData<List<Course>> getCoursesForTerm(UUID termId)
    {
        // Download courses for this term from server
        refreshCourseDataForTerm(termId);

        // Load courses associated with this term
        LiveData<List<Course>> courseLiveData = courseDao.loadCoursesForTerm( termId );
        courseLiveData = Transformations.switchMap(courseLiveData, inputCourses -> {
            MediatorLiveData<List<Course>> listMediatorLiveData = new MediatorLiveData<>();
            for(Course course : inputCourses) {
                if(course.getMentorId() != null) {
                    listMediatorLiveData.addSource(
                            facultyRepo.getFacultyMember(course.getMentorId()),
                            faculty -> {
                                if(faculty != null) {
                                    course.setMentor(faculty);
                                    listMediatorLiveData.setValue(inputCourses);
                                }
                            }
                    );
                } else {
                    Log.i(LOG_TAG, "Course: " + course.getId() + " does not have a mentor");
                }
            }
            return listMediatorLiveData;
        });

        return courseLiveData;
    }

    private void refreshCourseDataForTerm(final UUID termId)
    {
        Log.i(LOG_TAG, "refreshing courses for term: " + termId);

        executor.execute( () -> {
            try {
                final String authKey = App.getAuthorization().getAuthHeader();
                final UUID studentId = UUID.fromString( App.getAuthorization().getUserId() );

                Response<List<Course>> response =
                        webservice
                                .getCoursesForTerm(authKey, studentId, termId)
                                .execute();

                if(response.isSuccessful()) {
                    List<Course> courses = response.body();
                    // Save courses to DB
                    courses.forEach((c) -> {
                        Log.i(LOG_TAG, "Saving course: " + c);
                        courseDao.save(c);
                    });

                } else {
                    Log.e(LOG_TAG, "Response from server indicated fault: " + response.code());
                }

            } catch (IOException | RuntimeException e) {
                Log.e(
                    LOG_TAG,
                    "Error fetching courses for term: " + termId
                        +"\nMessage: " + e.getMessage()
                );
                e.printStackTrace();
            }
        });
    }
}
