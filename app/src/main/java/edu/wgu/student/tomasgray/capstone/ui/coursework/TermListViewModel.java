package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Term;


public class TermListViewModel extends AndroidViewModel
{
    private LiveData<List<Term>> termList;

    public TermListViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Initializes the data repository for this View, and
     * returns the number of terms returned from the repo
     *
     * @param termRepository
     * @return Number of Terms retrieved
     */
    void init(TermRepository termRepository) {
        this.termList = termRepository.getTerms();
    }

    LiveData<List<Term>> getTermList() {
        return termList;
    }
}
