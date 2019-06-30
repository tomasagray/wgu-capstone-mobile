package edu.wgu.student.tomasgray.capstone.data.access;

import androidx.room.Insert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static androidx.room.OnConflictStrategy.REPLACE;


public interface BaseDao<T>
{
    // Refresh rate, to prevent repeated pings of remote DB
    Duration refreshRate = Duration.of(5, ChronoUnit.SECONDS);

    @Insert(onConflict = REPLACE)
    void save(T data);
}
