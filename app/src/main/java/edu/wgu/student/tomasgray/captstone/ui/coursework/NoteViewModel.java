package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.access.NoteRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Note;

public class NoteViewModel extends AndroidViewModel
{
    private LiveData<Note> note;

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Note> getNote() {
        return note;
    }

    public void init(UUID noteId)
    {
        // Prevent multiple initializations
        if( note != null ) {
            return;
        }

        note = NoteRepository
                .getInstance(getApplication())
                .getNote(noteId);
    }
}
