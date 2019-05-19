package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
import edu.wgu.student.tomasgray.captstone.data.rest.AssessmentWebService;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;
import retrofit2.Response;

public class AssessmentRepository
{
    private static final String LOG_TAG = "AssessmentRepo";


    // Singleton
    // -------------------------------------------------------
    private static volatile AssessmentRepository INSTANCE;
    public static AssessmentRepository getInstance(Context context)
    {
        if(INSTANCE == null) {
            AssessmentDao assessmentDao
                    = AssessmentDatabase
                        .getInstance(context)
                        .assessmentDao();
            AssessmentWebService webService
                    = RestClient
                        .getInstance()
                        .create(AssessmentWebService.class);
            Executor executor = Executors.newCachedThreadPool();

            INSTANCE = new AssessmentRepository(assessmentDao, webService ,executor);
        }

        return INSTANCE;
    }

    // Fields
    private AssessmentDao assessmentDao;
    private AssessmentWebService webService;
    private Executor executor;

    // Constructor
    // --------------------------------------------------
    private AssessmentRepository(AssessmentDao assessmentDao, AssessmentWebService webService, Executor executor) {
        this.assessmentDao = assessmentDao;
        this.webService = webService;
        this.executor = executor;
    }


    // Data
    // ---------------------------------------------------
    public LiveData<List<Assessment>> getAllAssessments()
    {
        // Update local DB
        refreshAssessmentData();
        // Fetch data from local DB
        return assessmentDao.loadAll();
    }

    public LiveData<Assessment> getAssessment(UUID assessmentId)
    {
        // Update DB
        refreshAssessmentData();
        // Fetch specified Assessment from local DB
        return assessmentDao.load(assessmentId);
    }


    private void refreshAssessmentData()
    {
        // TODO: Implement skipping for repeated requests
        executor.execute(() -> {
//            boolean dataIsFresh = assessmentDao.isDataFresh();

            try {
                Response<List<Assessment>> response
                        = webService
                            .getAllAssessments()
                            .execute();

                // TODO: Error checking/validation

                // Extract response
                List<Assessment> assessments = response.body();
                // Save each assessment to local DB
                assessments.forEach(assessmentDao::save);

            } catch (IOException | RuntimeException e) {
                Log.e(
                        LOG_TAG,
                        "Could not refresh local assessment data from remote source:" +
                                "\nMessage: " + e.getMessage()
                );

                e.printStackTrace();
            }
        });
    }
}
