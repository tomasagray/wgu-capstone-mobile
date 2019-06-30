package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Converters;
import edu.wgu.student.tomasgray.capstone.data.model.Note;

// TODO: DELETE THIS
@Database( entities = {Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase
{
    // DAO
    public abstract NoteDao noteDao();

    // Singleton
    // ----------------------------------------------------------
    private static volatile NoteDatabase INSTANCE;
    static NoteDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE
                            = Room
                                .databaseBuilder(
                                        context,
                                        NoteDatabase.class,
                                        "note_database"
                                ).build();
                }
            }
        }
        return INSTANCE;
    }
}
