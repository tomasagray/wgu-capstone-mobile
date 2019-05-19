package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Assessment;

@Dao
public abstract class AssessmentDao implements BaseDao<Assessment>
{
    @Query("SELECT * FROM assessment WHERE id = :assessmentId")
    abstract LiveData<Assessment> load(UUID assessmentId);

    @Query("SELECT * FROM assessment")
    abstract LiveData<List<Assessment>> loadAll();
}
