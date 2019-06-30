package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.Term;

public class TermDetailViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "TermDetailVM";

    // LiveData
    private LiveData<Term> term;
    private LiveData<List<Course>> courses = new MutableLiveData<>();
    // Repos
    private TermRepository termRepository;
    private CourseRepository courseRepository;

    public TermDetailViewModel(@NonNull Application application) {
        super(application);

        // Initialize repos
        this.termRepository = TermRepository.getInstance(application);
        this.courseRepository = CourseRepository.getInstance(application);
    }

    void init(UUID termId) {
        Log.i(LOG_TAG, "Initializing VM with termID: " + termId.toString());
        this.term = termRepository.getTerm(termId);
    }

    void setCurrentTerm() {
        Log.i(LOG_TAG, "Setting current term");
        this.term = termRepository.getCurrentTerm();
    }

    public void setCourses(UUID termId) {
        Log.i(LOG_TAG, "Setting current courses for term: " + termId.toString());
//        this.courses = courseRepository.getCoursesForTerm(termId);
    }

    public LiveData<Term> getTerm() {
        return term;
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }
}
