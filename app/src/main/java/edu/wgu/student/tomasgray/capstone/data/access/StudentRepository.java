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

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.capstone.data.model.Student;
import edu.wgu.student.tomasgray.capstone.data.rest.RestClient;
import edu.wgu.student.tomasgray.capstone.data.rest.StudentWebService;
import edu.wgu.student.tomasgray.capstone.ui.App;
import retrofit2.Response;

public class StudentRepository
{
    private static final String LOG_TAG = "StudentRepo";

    // Singleton
    //--------------------------------------------------------
    private static volatile StudentRepository INSTANCE;
    public static StudentRepository getInstance(Context context) {
        if(INSTANCE == null) {
            StudentDao studentDao =
                    Database
                            .getInstance(context)
                            .studentDao();

            StudentWebService webService =
                    RestClient
                            .getInstance()
                            .create(StudentWebService.class);

            Executor executor = Executors.newCachedThreadPool();

            INSTANCE = new StudentRepository(studentDao, webService, executor);
        }

        return INSTANCE;
    }

    private StudentDao studentDao;
    private StudentWebService webService;
    private Executor executor;

    private StudentRepository(StudentDao studentDao, StudentWebService webService, Executor executor) {
        this.studentDao = studentDao;
        this.webService = webService;
        this.executor = executor;
    }

    public LiveData<Student> getStudentData() {
        // Update db
        refreshStudentData();

        return studentDao.load();
    }

    public void clearStudentData() {
        executor.execute( () -> {
            // Delete all student data from local database
            try {
                studentDao.deleteAll();

            } catch ( RuntimeException e ) {
                Log.e(
                        LOG_TAG,
                        "Caught error deleting student data: "
                            + "\nMessage: " + e.getMessage()
                );
                e.printStackTrace();
            }
        });
    }

    private void refreshStudentData()
    {
        executor.execute( () -> {
            if(studentDao.isDataFresh())
                return;

            try {
                // Get ID for logged-in student
                UUID studentId = App.getAuthorization().getStudent().getStudentId();
                String authKey = App.getAuthorization().getAuthHeader();

                Response<Student> response
                        = webService
                            .loadStudentData(authKey, studentId)
                            .execute();

                if(response.isSuccessful()) {
                    // Save data to local database
                    Student student = response.body();
                    studentDao.save(student);
                    Log.i(LOG_TAG, "Successfully saved student data for ID: " + student);
                } else {
                    Log.e(
                            LOG_TAG,
                            "Failed to get student data from server; code: " + response.code()
                    );
                }
            } catch (IOException | RuntimeException e) {
                Log.e(
                        LOG_TAG,
                        "Error getting student data!"
                            +"\nMessage: " + e.getMessage()
                );
                e.printStackTrace();
            }
        });
    }
}
