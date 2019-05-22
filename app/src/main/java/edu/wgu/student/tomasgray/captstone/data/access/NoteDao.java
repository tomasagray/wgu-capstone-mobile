package edu.wgu.student.tomasgray.captstone.data.access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.model.Note;

@Dao
public abstract class NoteDao implements BaseDao<Note>
{
    @Query("SELECT * FROM note WHERE noteId = :noteId")
    abstract LiveData<Note> load(UUID noteId);

    @Query("SELECT * FROM note ORDER BY updateDate")
    abstract LiveData<List<Note>> loadAll();
}
