package edu.wgu.student.tomasgray.captstone.data.access;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Term;

@Dao
abstract class TermDao implements BaseDao<Term>
{
    @Query("SELECT * FROM term WHERE termId = :term_id")
    abstract LiveData<Term> load(UUID term_id);

    @Query("SELECT * FROM term")
    abstract LiveData<List<Term>> loadAll();

    @Query("SELECT * FROM term WHERE startDate <= :now AND endDate >= :now LIMIT 1")
    abstract Cursor loadCurrent(LocalDate now);
}
