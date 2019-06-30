package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.CourseRepository;
import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Term;

// TODO: DELETE THIS CLASS
public class OverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "OverviewVM";


    // Repos
    private CourseRepository courseRepo;
    private TermRepository termRepo;
    // LiveData
//    private LiveData<List<Course>> courses;
    private LiveData<Term> term;

    public OverviewViewModel(@NonNull Application application) {
        super(application);
        // Save repo reference
        this.courseRepo = CourseRepository.getInstance(getApplication());
        this.termRepo = TermRepository.getInstance(getApplication());
    }

    void init(UUID termId) {
        // Get Term LiveData
        term = termRepo.getTerm(termId);
    }

    public LiveData<Term> getTerm() {
        return term;
    }

}
