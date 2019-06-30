package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;

import edu.wgu.student.tomasgray.capstone.data.model.Student;

// TODO: DELETE this class
@Dao
abstract class StudentDao implements BaseDao<Student>
{
    private static final String LOG_TAG = "StudentDao";

    // Initialize to 1970
    private Timestamp lastRefresh = new Timestamp(0);


    @Query("SELECT * FROM student")
    abstract LiveData<Student> load();

    @Query("DELETE FROM student")
    abstract void deleteAll();


    // Prevent server hammering
    public boolean isDataFresh()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Duration timeSinceRefresh = Duration.between(lastRefresh.toInstant(), currentTime.toInstant());
        if(timeSinceRefresh.compareTo(refreshRate) > 0) {
            Log.i(LOG_TAG, "Student Data is expired, need refresh: " + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Student Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
