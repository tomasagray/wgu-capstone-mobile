package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.access.AssessmentRepository;
import edu.wgu.student.tomasgray.captstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.captstone.data.access.FacultyRepository;
import edu.wgu.student.tomasgray.captstone.data.access.NoteRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.Faculty;
import edu.wgu.student.tomasgray.captstone.data.model.Note;

public class CourseDetailViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "CourseDetailVM";

    private LiveData<Course> course;
    private LiveData<Faculty> mentor;
    private LiveData<List<Assessment>> assessments;
    private LiveData<List<Note>> notes;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(UUID courseId)
    {
        course = CourseRepository
                    .getInstance(getApplication())
                    .getCourse(courseId);

        // TODO: Fix this! Get the mentor assoc. with this course!
        UUID mentorId = UUID.fromString("0eb85ad9-c46d-4d56-b9d9-6f57aec123a6");
        mentor = FacultyRepository
                    .getInstance(getApplication())
                    .getFacultyMember(mentorId);

        // TODO: FIX THIS! Get assoc. assessments
        assessments = AssessmentRepository
                        .getInstance(getApplication())
                        .getAllAssessments();

        // TODO: FIX THIS! Get assoc. notes
        notes = NoteRepository
                    .getInstance(getApplication())
                    .getAllNotes();
    }

    public LiveData<Course> getCourse() {
        return course;
    }
    public LiveData<Faculty> getMentor() {
        return mentor;
    }
    public LiveData<List<Assessment>> getAssessments() {
        return assessments;
    }
    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}
