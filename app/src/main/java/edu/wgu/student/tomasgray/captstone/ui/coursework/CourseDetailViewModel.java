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
import edu.wgu.student.tomasgray.captstone.data.model.Assessment;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.Faculty;

public class CourseDetailViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "CourseDetailVM";

    private LiveData<Course> course;
    private LiveData<Faculty> mentor;
    private LiveData<List<Assessment>> assessments;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(UUID courseId)
    {
        course = CourseRepository
                    .getInstance(getApplication())
                    .getCourse(courseId);

        // TODO: Fix this! Get the mentor assoc. with this course!
        UUID mentorId = UUID.fromString("2f153641-9c88-4bd9-aa93-cac40340c46a");
        mentor = FacultyRepository
                    .getInstance(getApplication())
                    .getFacultyMember(mentorId);

        // TODO: FIX THIS! Get assoc. assessments
        assessments = AssessmentRepository
                        .getInstance(getApplication())
                        .getAllAssessments();
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
}
