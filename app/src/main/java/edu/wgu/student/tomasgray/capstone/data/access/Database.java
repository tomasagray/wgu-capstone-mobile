package edu.wgu.student.tomasgray.capstone.data.access;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.student.tomasgray.capstone.data.model.Assessment;
import edu.wgu.student.tomasgray.capstone.data.model.Converters;
import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;
import edu.wgu.student.tomasgray.capstone.data.model.Note;
import edu.wgu.student.tomasgray.capstone.data.model.Student;
import edu.wgu.student.tomasgray.capstone.data.model.Term;

@androidx.room.Database(
        entities = {
                Term.class,
                Assessment.class,
                Course.class,
                Faculty.class,
                Student.class,
                Note.class
        },
        version = 1
)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase
{
    // DAOs
    // --------------------------------------------------------
    abstract TermDao termDao();
    abstract CourseDao courseDao();
    abstract AssessmentDao assessmentDao();
    abstract FacultyDao facultyDao();
    abstract StudentDao studentDao();
    abstract NoteDao noteDao();


    // Singleton
    // --------------------------------------------------------
    private static volatile Database INSTANCE;
    public static Database getInstance(Context context)
    {
        if(INSTANCE == null) {
            synchronized (Database.class) {
                if(INSTANCE == null) {
                    INSTANCE
                            = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            "hku_student_database"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }

}
