package edu.wgu.student.tomasgray.captstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Faculty;

@Dao
abstract class FacultyDao implements BaseDao<Faculty>
{
    private static final String LOG_TAG = "FacultyDAO";


//    private static final Duration refreshRate = Duration.of(5, ChronoUnit.SECONDS);
    private static final Long refreshRate = 5_000L;
    // Initialize on first instance
    private static Long lastRefresh = System.currentTimeMillis()/1_000L;

    @Query("SELECT * FROM faculty WHERE facultyId = :facultyId")
    abstract LiveData<Faculty> load(UUID facultyId);

    @Query("SELECT * FROM faculty")
    abstract LiveData<List<Faculty>> loadAll();


    boolean isDataFresh()
    {
        Long currentTime = System.currentTimeMillis()/1_000L;
        Long timeSinceRefresh = Math.abs(currentTime - lastRefresh);
        if(timeSinceRefresh > refreshRate) {
            Log.i(LOG_TAG, "Data is expired, need refresh:" + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
