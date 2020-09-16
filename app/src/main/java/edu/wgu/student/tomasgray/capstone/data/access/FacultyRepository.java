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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.capstone.data.model.Faculty;
import edu.wgu.student.tomasgray.capstone.data.rest.FacultyWebService;
import edu.wgu.student.tomasgray.capstone.data.rest.RestClient;
import edu.wgu.student.tomasgray.capstone.ui.App;
import retrofit2.Response;

public class FacultyRepository
{
    private static final String LOG_TAG = "FacultyRepo";

    // Singleton
    // ---------------------------------------------------------------------
    private static volatile FacultyRepository INSTANCE;
    public static FacultyRepository getInstance(Context context)
    {
        if(INSTANCE == null) {
            // DAO
            FacultyDao dao
                    = Database
                        .getInstance(context)
                        .facultyDao();
            // Webservice
            FacultyWebService webService
                    = RestClient
                        .getInstance()
                        .create(FacultyWebService.class);
            // Thread scheduler
            ExecutorService executor = Executors.newCachedThreadPool();

            INSTANCE = new FacultyRepository(dao, webService, executor);
        }

        return INSTANCE;
    }

    // Fields
    // ----------------------------------------------------------------------
    private FacultyDao facultyDao;
    private FacultyWebService webService;
    private Executor executor;

    // Constructor
    // ----------------------------------------------------------------------
    private FacultyRepository(FacultyDao dao, FacultyWebService webService, Executor executor) {
        this.facultyDao = dao;
        this.webService = webService;
        this.executor = executor;
    }

    // Getters
    // ----------------------------------------------------------------------
    public LiveData<List<Faculty>> getAllFaculty(){
        refreshFacultyData();
        return facultyDao.loadAll();
    }

    public LiveData<Faculty> getFacultyMember(UUID facultyId)
    {
        refreshFacultyData();
        return facultyDao.load(facultyId);
    }

    /**
     * Refresh data in local DB by scheduling a task to fetch data
     * from remote server.
     *
     */
    private void refreshFacultyData()
    {
        executor.execute(() -> {
            // if is has been done recently
            if(facultyDao.isDataFresh())
                return;

            Log.i(LOG_TAG, "Refreshing faculty data");

            try {
                final String authKey = App.getAuthorization().getAuthHeader();

                Response<List<Faculty>> response
                        = webService
                            .getAllFaculty(authKey)
                            .execute();

                if(response.isSuccessful()) {
                    // Extract response
                    List<Faculty> faculty = response.body();
                    // TODO: Error validation

                    // Clear old data
                    facultyDao.deleteAll();
                    // Save each to local DB
                    faculty.forEach(fac -> {
                        Log.i(LOG_TAG, "Got faculty member from remote server: " + fac.getFacultyId());
                        facultyDao.save(fac);
                    });
                } else {
                    Log.e(
                            LOG_TAG,
                            "Error getting faculty data from server!"
                                    +"\nMessage: " + response.code()
                    );
                }

            } catch (IOException | NullPointerException e) {
                Log.e(LOG_TAG, "Error fetching faculty data from server: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
