package edu.wgu.student.tomasgray.captstone.data.access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
import edu.wgu.student.tomasgray.captstone.data.model.Converters;


@Database( entities = {Assessment.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AssessmentDatabase extends RoomDatabase
{
    // DAO
    public abstract AssessmentDao assessmentDao();

    // Singleton
    // --------------------------------------------------
    private static volatile AssessmentDatabase INSTANCE;
    static AssessmentDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (AssessmentDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room
                                .databaseBuilder(
                                        context.getApplicationContext(),
                                        AssessmentDatabase.class,
                                        "assessment_database"
                                ).build();
                }
            }
        }
        return INSTANCE;
    }
}
