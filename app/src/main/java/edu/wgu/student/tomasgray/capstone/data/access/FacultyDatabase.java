package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Converters;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

// todo: DELETE this class
@Database(entities = {Faculty.class}, version =  1)
@TypeConverters({Converters.class})
public abstract class FacultyDatabase extends RoomDatabase
{
    // DAO
    public abstract FacultyDao facultyDao();

    // Singleton
    // -----------------------------------------------------------
    private static volatile FacultyDatabase INSTANCE;
    static FacultyDatabase getInstance(final Context context)
    {
        if(INSTANCE == null) {
            synchronized (FacultyDatabase.class) {
                if(INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FacultyDatabase.class,
                            "faculty_database"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }
}
