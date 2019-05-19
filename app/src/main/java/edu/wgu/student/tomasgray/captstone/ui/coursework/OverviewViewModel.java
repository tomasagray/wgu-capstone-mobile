package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.Term;

public class OverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "OverviewVM";


    // Repos
    private CourseRepository courseRepo;
    private TermRepository termRepo;
    // LiveData
    private LiveData<List<Course>> courses;
    private LiveData<Term> term;

    public OverviewViewModel(@NonNull Application application) {
        super(application);
        // Save repo reference
        this.courseRepo = CourseRepository.getInstance(getApplication());
        this.termRepo = TermRepository.getInstance(getApplication());
    }

    void init(UUID termId)
    {
        if (this.courses != null && this.term != null) {
            return;
        }

        // Get Term LiveData
        term = termRepo.getTerm(termId);
        // Get Course LiveData
        courses = courseRepo.getCoursesForTerm(termId);
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }
    public LiveData<Term> getTerm() {
        return term;
    }

}
