package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.room.Insert;

import static androidx.room.OnConflictStrategy.REPLACE;


public interface BaseDao<T>
{
    @Insert(onConflict = REPLACE)
    void save(T data);
}
