package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.data.model.Note;
import edu.wgu.student.tomasgray.captstone.data.rest.NoteWebService;
import edu.wgu.student.tomasgray.captstone.data.rest.RestClient;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class NoteRepository
{
    private static final String LOG_TAG = "NoteRepo";

    // Singleton
    // ---------------------------------------------------------
    private static volatile NoteRepository INSTANCE;
    public static NoteRepository getInstance(Context context)
    {
        if(INSTANCE == null) {
            NoteDao noteDao
                    = NoteDatabase
                        .getInstance(context)
                        .noteDao();
            NoteWebService webService
                    = RestClient
                        .getInstance()
                        .create(NoteWebService.class);
            Executor executor
                    = Executors.newCachedThreadPool();

            INSTANCE = new NoteRepository(noteDao, webService, executor);
        }

        return INSTANCE;
    }

    // Fields
    private NoteDao noteDao;
    private NoteWebService webService;
    private Executor executor;

    // Constructor
    private NoteRepository(NoteDao noteDao, NoteWebService webService, Executor executor) {
        this.noteDao = noteDao;
        this.webService = webService;
        this.executor = executor;
    }

    // Getters
    // ---------------------------------------------------------
    public LiveData<List<Note>> getAllNotes()
    {
        Log.i(LOG_TAG, "Getting all notes");

        // Update local data
        refreshNoteData();
        // Fetch all Notes
        return noteDao.loadAll();
    }

    public LiveData<Note> getNote(UUID noteId)
    {
        // Refresh local DB
        refreshNoteData();
        // Fetch requested Note
        return noteDao.load(noteId);
    }

    // Savers
    // ---------------------------------------------------------
    public void saveNote(@NonNull Note note)
    {
        Log.i(LOG_TAG, "Saving new note:\n" + note);

        executor.execute(() -> {
            try {
                Response<Note> response
                        = webService
                            .saveNote(note)
                            .execute();

                if(response != null) {
                    Log.i(
                            LOG_TAG,
                            "Got response from server when saving note: (c): " +
                                    response.code() + " : " + response.toString()
                    );
                } else {
                    Log.i(LOG_TAG, "Response was null");
                }
            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Caught exception: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void updateNote(@NonNull Note note)
    {
        Log.i(LOG_TAG, "Saving note:\n" + note);

        executor.execute(() -> {
            // Save to remote source
            try {
                Response<ResponseBody> response
                        = webService
                        .updateNote(note.getNoteId(), note)
                        .execute();

                if(response != null)
                    Log.i(LOG_TAG, "Got response to update: (c)" + response.code() + " : " + response.body());
                else {
                    Log.i(LOG_TAG, "Response was null!");
                }
            } catch (IOException | RuntimeException e) {
                Log.e(LOG_TAG, "Caught exception: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Update local DB
        refreshNoteData();
    }

    private void refreshNoteData()
    {
        // TODO: Prevent repeat calls
        executor.execute(() -> {
//            boolean dataFresh = noteDao.isDataFresh();
//            if(dataFresh) {
//                return;
//            }

            try {
                Response<List<Note>> response
                        = webService
                            .getAllNotes()
                            .execute();

                // TODO: Error / validation
                List<Note> notes = response.body();

                // Save Notes to local DB
                notes.forEach(noteDao::save);

            } catch (IOException | RuntimeException e ) {
                Log.e(
                        LOG_TAG,
                        "Could not update Note data from remote source" +
                                "\n\tMessage: " + e.getMessage()
                );

                e.printStackTrace();
            }
        });
    }
}
