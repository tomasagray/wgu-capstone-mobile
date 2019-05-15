package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }
    public LiveData<Term> getTerm() {
        return term;
    }

    void init()
    {
        if (this.courses != null && this.term != null) {
            return;
        }

        // Save repo reference
        this.courseRepo = CourseRepository.getInstance(getApplication());
        this.termRepo = TermRepository.getInstance(getApplication());
        // Get LiveData
        /*term = termRepo.getCurrentTerm(LocalDate.now());
        Log.i(LOG_TAG, "Term value: " + term.getValue());

        if(term.getValue() != null)
           courses = courseRepo.getCoursesForTerm( term.getValue().getTermId() );*/
    }
}
