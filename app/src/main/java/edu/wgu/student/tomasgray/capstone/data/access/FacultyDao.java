package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

@Dao
abstract class FacultyDao implements BaseDao<Faculty>
{
    private static final String LOG_TAG = "FacultyDAO";

    // Initialize to 1970
    private Timestamp lastRefresh = new Timestamp(0);

    @Query("SELECT * FROM faculty WHERE facultyId = :facultyId")
    abstract LiveData<Faculty> load(UUID facultyId);

    @Query("SELECT * FROM faculty")
    abstract LiveData<List<Faculty>> loadAll();

    @Query("DELETE FROM faculty")
    abstract void deleteAll();

    public boolean isDataFresh()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Duration timeSinceRefresh = Duration.between(lastRefresh.toInstant(), currentTime.toInstant());
        if(timeSinceRefresh.compareTo(refreshRate) > 0) {
            Log.i(LOG_TAG, "Data is expired, need refresh:" + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
