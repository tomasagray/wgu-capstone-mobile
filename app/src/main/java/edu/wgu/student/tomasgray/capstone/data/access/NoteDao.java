package edu.wgu.student.tomasgray.capstone.data.access;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.model.Note;

@Dao
public abstract class NoteDao implements BaseDao<Note>
{
    private static final String LOG_TAG = "NoteDao";

    // Initialize to current time
    Timestamp lastRefresh = new Timestamp(System.currentTimeMillis());

    @Query("SELECT * FROM note WHERE noteId = :noteId")
    abstract LiveData<Note> load(UUID noteId);

    @Query("SELECT * FROM note ORDER BY updateDate")
    abstract LiveData<List<Note>> loadAll();

    @Query("DELETE FROM note")
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
