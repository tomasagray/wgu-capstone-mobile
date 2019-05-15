package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Student;

@Dao
abstract class StudentDao implements BaseDao<Student>
{
    @Query("SELECT * FROM student WHERE studentId = :studentId")
    abstract LiveData<Student> load(UUID studentId);
}
