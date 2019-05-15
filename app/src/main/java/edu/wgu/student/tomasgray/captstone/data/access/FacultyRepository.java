package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.model.Faculty;
import edu.wgu.student.tomasgray.captstone.data.rest.FacultyWebService;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;
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
                    = FacultyDatabase
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
        Log.i(LOG_TAG, "Getting all faculty: " + facultyDao.loadAll().getValue());

        refreshFacultyData();
        return facultyDao.loadAll();
    }

    public LiveData<Faculty> getFacultyMember(UUID facultyId)
    {
        Log.i(LOG_TAG, "Retrieving data for faculty ID: " + facultyId);
        refreshFacultyData();

        LiveData<Faculty> faculty = facultyDao.load(facultyId);
        Log.i(LOG_TAG, "Retrieved: " + faculty.getValue());

        return faculty;
    }

    /**
     * Refresh data in local DB by scheduling a task to fetch data
     * from remote server.
     *
     */
    private void refreshFacultyData()
    {
        executor.execute(() -> {
            // TODO: Don't refresh data from remote source
            // if is has been done recently
//            if(facultyDao.isDataFresh())
//                return;

            Log.i(LOG_TAG, "Refreshing faculty data");

            try {
                Response<List<Faculty>> response
                        = webService
                            .getAllFaculty()
                            .execute();

                if(response.code() == 200) {
                    // Extract response
                    List<Faculty> faculty = response.body();
                    // TODO: Error validation

                    // Save each to local DB
                    faculty.forEach(fac -> {
                        Log.i(LOG_TAG, "Got faculty member from remote server: " + fac);
                        facultyDao.save(fac);
                    });
                } else {
                    Log.e(
                            LOG_TAG,
                            "Response code: " + response.code()
                            +"\nError: " + response.errorBody().string()
                    );
                }

            } catch (IOException | NullPointerException e) {
                Log.e(LOG_TAG, "Error fetching data from server: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
