package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Assessment;

@Dao
public abstract class AssessmentDao implements BaseDao<Assessment>
{
    private static final String LOG_TAG = "AssessmentDao";

    // Initialize to 1970 - very stale!
    Timestamp lastRefresh = new Timestamp(0);

    @Query("SELECT * FROM assessment WHERE id = :assessmentId")
    abstract LiveData<Assessment> load(UUID assessmentId);

    @Query("SELECT * FROM assessment")
    abstract LiveData<List<Assessment>> loadAll();

    @Query("SELECT * FROM assessment WHERE courseId = :courseId")
    abstract LiveData<List<Assessment>> loadForCourse(UUID courseId);


    @Query("DELETE FROM assessment")
    abstract void deleteAll();

    public boolean isDataFresh()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Duration timeSinceRefresh = Duration.between(lastRefresh.toInstant(), currentTime.toInstant());
        if(timeSinceRefresh.compareTo(refreshRate) > 0) {
            Log.i(LOG_TAG, "Assessment Data is expired, need refresh: " + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Assessment Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
