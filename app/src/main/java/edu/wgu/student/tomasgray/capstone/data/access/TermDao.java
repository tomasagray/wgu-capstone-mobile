package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Term;

@Dao
abstract class TermDao implements BaseDao<Term>
{
    private static final String LOG_TAG = "TermDAO";

    // Initialize to 1970
    private Timestamp lastRefresh = new Timestamp(0);


    @Query("SELECT * FROM term WHERE termId = :term_id")
    abstract LiveData<Term> load(UUID term_id);

    @Query("SELECT * FROM term ORDER BY startDate")
    abstract LiveData<List<Term>> loadAll();

    // TODO : Implement this!
    /*@Query("SELECT * FROM term WHERE :now BETWEEN startDate AND endDate ORDER BY startDate LIMIT 1")
    abstract LiveData<Term> loadCurrent(LocalDate now);*/

    // TODO: Delete method, replace with above
    @Query("SELECT * FROM term LIMIT 1")
    abstract LiveData<Term> loadCurrent();

    @Query("DELETE FROM term")
    abstract void deleteAll();

    public boolean isDataFresh()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Duration timeSinceRefresh = Duration.between(lastRefresh.toInstant(), currentTime.toInstant());
        if(timeSinceRefresh.compareTo(refreshRate) > 0) {
            Log.i(LOG_TAG, "Data is expired, need refresh: " + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
