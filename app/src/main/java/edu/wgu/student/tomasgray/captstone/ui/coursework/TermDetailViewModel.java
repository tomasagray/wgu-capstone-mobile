package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.Term;

public class TermDetailViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "TermDetailVM";

    // LiveData
    private LiveData<Term> term;
    private LiveData<List<Course>> courses;
    // Repos
    private TermRepository termRepository;
    private CourseRepository courseRepository;

    public TermDetailViewModel(@NonNull Application application) {
        super(application);

        // Initialize repos
        this.termRepository = TermRepository.getInstance(getApplication());
        this.courseRepository = CourseRepository.getInstance(getApplication());
    }

    void init(UUID termId)
    {
        Log.i(LOG_TAG, "Initializing VM with termID: " + termId.toString());

        if(this.term != null) {
            return;
        }


        this.term = termRepository.getTerm(termId);
        // TODO: FIX THIS!
//        this.courses = courseRepository.getCoursesForTerm(termId);
        this.courses = courseRepository.getAllCourses();

        Log.i(LOG_TAG, "Term value: " + term.getValue());
    }

    public LiveData<Term> getTerm() {
//        Log.i(LOG_TAG, "Term value: " + ((term == null) ? "nuLL" : term.getValue().toString()));
        return term;
    }

    public LiveData<List<Course>> getCourses() {
//        Log.i(LOG_TAG, "Courses are: " + ((courses != null) ? "nuLL" : courses.getValue()));
        return courses;
    }
}
