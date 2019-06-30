package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Converters;
import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

// TODO: Delete this class
@Database(entities = {Course.class, Faculty.class}, version =  1)
@TypeConverters({Converters.class})
public abstract class CourseDatabase extends RoomDatabase
{
    // DAO
    public abstract CourseDao courseDao();

    // Singleton
    // -----------------------------------------------------------
    private static volatile CourseDatabase INSTANCE;
    static CourseDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (CourseDatabase.class) {
                if(INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    CourseDatabase.class,
                                    "course_database"
                               ).build();
                }
            }
        }
        return INSTANCE;
    }
}
