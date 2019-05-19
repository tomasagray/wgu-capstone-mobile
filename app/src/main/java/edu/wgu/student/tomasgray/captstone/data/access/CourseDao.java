package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Course;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
abstract class CourseDao // implements BaseDao<Course>
{
    @Query("SELECT * FROM course WHERE id = :id")
    abstract LiveData<Course> load(UUID id);

    @Query("SELECT * FROM course")
    abstract LiveData<List<Course>> loadAll();

    @Query("SELECT * FROM course WHERE startDate <= :now AND endDate >= :now")
    abstract LiveData<List<Course>> loadCurrent(Date now);

    @Query("SELECT * FROM course WHERE course.termId = :termId")
    abstract LiveData<List<Course>> loadCoursesForTerm(UUID termId);

    @Insert(onConflict = REPLACE)
    abstract void save(Course course);
}
