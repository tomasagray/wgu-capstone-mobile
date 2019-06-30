package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.AssessmentRepository;
import edu.wgu.student.tomasgray.capstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.capstone.data.access.NoteRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Assessment;
import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.Note;

public class CourseDetailViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "CourseDetailVM";

    private LiveData<Course> course;
    private LiveData<List<Assessment>> assessments;
    private LiveData<List<Note>> notes;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(UUID courseId)
    {
        Log.i(LOG_TAG, "initing VM with coursedeets ID: " + courseId);
        course = CourseRepository
                    .getInstance(getApplication())
                    .getCourse(courseId);

        assessments = AssessmentRepository
                        .getInstance(getApplication())
                        .getAssessmentsForCourse(courseId);

        // TODO: FIX THIS! Get assoc. notes
        notes = NoteRepository
                    .getInstance(getApplication())
                    .getAllNotes();
    }

    public LiveData<Course> getCourse() {
        return course;
    }
    public LiveData<List<Assessment>> getAssessments() {
        return assessments;
    }
    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}
