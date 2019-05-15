package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Term;


public class TermListViewModel extends AndroidViewModel
{
    private TermRepository termRepository;
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
    public void init(TermRepository termRepository)
    {
        this.termRepository = termRepository;
        this.termList = termRepository.getTerms();
    }

    public LiveData<List<Term>> getTermList() {
        return termList;
    }
}
