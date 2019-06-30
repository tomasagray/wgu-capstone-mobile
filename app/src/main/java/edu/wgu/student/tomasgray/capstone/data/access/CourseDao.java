package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Course;

@Dao
abstract class CourseDao implements BaseDao<Course>
{
    private static final String LOG_TAG = "CourseDAO";

    // Initialize to 1970
    Timestamp lastRefresh = new Timestamp(0);

    @Query("SELECT * FROM course WHERE id = :id")
    abstract LiveData<Course> load(UUID id);

    @Query("SELECT * FROM course")
    abstract LiveData<List<Course>> loadAll();

    @Query("SELECT * FROM course WHERE startDate <= :now AND endDate >= :now")
    abstract LiveData<List<Course>> loadCurrent(LocalDate now);

    @Query("SELECT * FROM course WHERE course.termId = :termId")
    abstract LiveData<List<Course>> loadCoursesForTerm(UUID termId);


    @Query("DELETE FROM course")
    abstract void deleteAll();

    public boolean isDataFresh()
    {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Duration timeSinceRefresh = Duration.between(lastRefresh.toInstant(), currentTime.toInstant());
        if(timeSinceRefresh.compareTo(refreshRate) > 0) {
            Log.i(LOG_TAG, "Course Data is expired, need refresh: " + timeSinceRefresh);
            lastRefresh = currentTime;
            return false;
        } else {
            Log.i(LOG_TAG, "Course Data is already fresh: " + timeSinceRefresh);
            return true;
        }
    }
}
