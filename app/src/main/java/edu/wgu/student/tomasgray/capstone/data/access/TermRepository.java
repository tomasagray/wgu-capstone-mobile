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
import edu.wgu.student.tomasgray.capstone.data.model.Term;
import edu.wgu.student.tomasgray.capstone.data.rest.Authorization;
import edu.wgu.student.tomasgray.capstone.data.rest.RestClient;
import edu.wgu.student.tomasgray.capstone.data.rest.TermWebService;
import edu.wgu.student.tomasgray.capstone.ui.App;
import retrofit2.Response;

public class TermRepository {
    private static final String LOG_TAG = "TermRepository";


    // Singleton
    // --------------------------------------------------------------------------
    private static volatile TermRepository instance;

    public static TermRepository getInstance(Context context) {
        if (instance == null) {
            // Get a ref to DAO
            TermDao termDao =
                    Database
                            .getInstance(context)
                            .termDao();
            // For course data access
            CourseRepository courseRepository = CourseRepository.getInstance(context);

            // Define a REST service
            TermWebService webService =
                    RestClient
                            .getInstance()
                            .create(TermWebService.class);
            // Create an Executor
            ExecutorService executor = Executors.newCachedThreadPool();

            instance = new TermRepository(termDao, courseRepository, webService, executor);
        }

        return instance;
    }

    private final TermDao termDao;
    private final CourseRepository courseRepository;
    private final TermWebService webservice;
    private final Executor executor;

    // Constructors
    // --------------------------------------------------------------------------
    private TermRepository(TermDao termDao, CourseRepository courseRepository, TermWebService webservice, Executor executor) {
        this.termDao = termDao;
        this.courseRepository = courseRepository;
        this.webservice = webservice;
        this.executor = executor;
        LoginRepository loginRepository = LoginRepository.getInstance();
    }


    // Data access
    // -------------------------------------------------------------------------
    public LiveData<Term> getTerm(UUID termId) {
        Log.i(LOG_TAG, "Fetching data for term: " + termId.toString());
        // Get fresh data
        refreshTermData();
        // Load term data
        LiveData<Term> termLiveData = termDao.load(termId);
        termLiveData = Transformations.switchMap(termLiveData, inputTerm -> {
            if (inputTerm == null)
                return null;
            LiveData<List<Course>> coursesLiveData =
                    courseRepository.getCoursesForTerm(inputTerm.getTermId());
            return Transformations.map(coursesLiveData, inputCourses -> {
                inputTerm.setCourseList(inputCourses);
                return inputTerm;
            });
        });

        return termLiveData;
    }

    public LiveData<List<Term>> getTerms() {
        // Ensure local data is up-to-date
        refreshTermData();
        // Fetch data from local DB
        LiveData<List<Term>> listLiveData = termDao.loadAll();

        listLiveData = Transformations.switchMap(listLiveData, inputTerms -> {
            MediatorLiveData<List<Term>> listMediatorLiveData = new MediatorLiveData<>();
            for (final Term term : inputTerms) {
                listMediatorLiveData.addSource(
                        courseRepository.getCoursesForTerm(term.getTermId()),
                        courses -> {
                            Log.i(LOG_TAG, "Adding CourseList for term: " + term.getTermId() + ", course #: " + courses.size());
                            term.setCourseList(courses);
                            Log.i(LOG_TAG, "Term " + term.getTermId() + " CourseList size is: " + term.getCourseList().size());
                            listMediatorLiveData.setValue(inputTerms);
                        }
                );
            }
            return listMediatorLiveData;
        });

        return listLiveData;
    }

    public LiveData<Term> getCurrentTerm() {
        // Refresh local DB
        refreshTermData();
        // Get currently enrolled term
        LiveData<Term> termLiveData = termDao.loadCurrent(); //termDao.loadCurrent( LocalDate.now() );

        // Fetch courses for this term when observers attach
        termLiveData = Transformations.switchMap(termLiveData, inputTerm -> {
            if (inputTerm == null)
                return null;
            LiveData<List<Course>> coursesLiveData =
                    courseRepository.getCoursesForTerm(inputTerm.getTermId());
            return Transformations.map(coursesLiveData, inputCourses -> {
                inputTerm.setCourseList(inputCourses);
                return inputTerm;
            });
        });

        return termLiveData;
    }

    // Server side
    // ------------------------------------------------------------------------
    private void refreshTermData() {
        Log.i(LOG_TAG, "Refreshing Term data");
        Authorization authorization = App.getAuthorization();
        executor.execute(() -> {
            // Skip if we have recently updated
            if (termDao.isDataFresh())
                return;

            try {
                Response<List<Term>> response
                        = webservice
                        .getAllTerms(
                                authorization.getAuthHeader(),
                                authorization.getUserId()
                        )
                        .execute();
                // Extract response data
                List<Term> terms = response.body();
                if (response.isSuccessful() && terms != null && terms.size() > 0) {
                    // TODO: Check for errors
                    // Clear old data
                    termDao.deleteAll();
                    // Add each term to DB
                    terms.forEach(termDao::save);
                } else {
                    Log.e(LOG_TAG, "Error fetching term data from server: " + response);
                }

            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Error fetching term data from server:\n" + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
