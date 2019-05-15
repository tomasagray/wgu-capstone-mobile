package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.model.Term;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;
import edu.wgu.student.tomasgray.captstone.data.rest.TermWebService;
import retrofit2.Response;

public class TermRepository
{
    private static final String LOG_TAG = "TermRepository";


    // Singleton
    // --------------------------------------------------------------------------
    private static volatile TermRepository instance;

    public static TermRepository getInstance(Context context) {
        if (instance == null) {
            // Get a ref to DAO
            TermDao termDao =
                    TermDatabase
                            .getInstance(context)
                            .termDao();
            // Define a REST service
            TermWebService webService =
                    RestClient
                            .getInstance()
                            .create(TermWebService.class);
            // Create an Executor
            ExecutorService executor = Executors.newCachedThreadPool();

            instance = new TermRepository(termDao, webService, executor);
        }

        return instance;
    }

    private final TermDao termDao;
    private final TermWebService webservice;
    private final Executor executor;

    // Constructors
    // --------------------------------------------------------------------------
    private TermRepository(TermDao termDao, TermWebService webservice, Executor executor) {
        this.termDao = termDao;
        this.webservice = webservice;
        this.executor = executor;
    }

    public LiveData<Term> getTerm(UUID termId)
    {
        Log.i(LOG_TAG, "Fetching data for term: " + termId.toString());
        Log.i(LOG_TAG, "Data: " + termDao.load(termId).getValue());

        // Get fresh data
        refreshTermData();
        // Load term data
        return termDao.load(termId);
    }

    public LiveData<List<Term>> getTerms()
    {
        // Ensure local data is up-to-date
        refreshTermData();
        // Fetch data from local DB
        return termDao.loadAll();
    }

    public Cursor getCurrentTerm(final LocalDate now)
    {
        // Refresh local DB
        refreshTermData();
        // Get currently enrolled term
        return termDao.loadCurrent(now);
    }

    private void refreshTermData()
    {
        Log.i(LOG_TAG, "Refreshing Term data");

        executor.execute(() -> {
            // TODO: add blocker for frequency
            try {
                Response<List<Term>> response
                        = webservice
                            .getAllTerms()
                            .execute();
                // Extract response data
                List<Term> terms = response.body();

                // TODO: Check for errors

                // Add each term to DB
                terms.forEach((term) -> {
                    Log.i(LOG_TAG, "Retrieved term from remote server: " + term.toString());
                    termDao.save(term);
                });
            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Error fetching term data from server:\n" + e.getMessage());

                e.printStackTrace();
            }
        });
    }
}
