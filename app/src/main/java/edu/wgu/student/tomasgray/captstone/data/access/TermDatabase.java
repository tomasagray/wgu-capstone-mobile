package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.captstone.data.model.Converters;
import edu.wgu.student.tomasgray.captstone.data.model.Term;

@Database(entities = {Term.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TermDatabase extends RoomDatabase
{
    // DAO
    public abstract TermDao termDao();

    // Singleton
    // -----------------------------------------------------
    private static volatile TermDatabase INSTANCE;

    static TermDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (TermDatabase.class) {
                if(INSTANCE == null) {
                    // Create DB
                    INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                TermDatabase.class,
                                "term_database"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }
}
