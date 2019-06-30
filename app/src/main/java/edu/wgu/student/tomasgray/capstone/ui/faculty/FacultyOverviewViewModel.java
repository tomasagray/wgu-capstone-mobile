package edu.wgu.student.tomasgray.capstone.ui.faculty;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.data.access.FacultyRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

public class FacultyOverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "FacultyOvVM";

    private LiveData<List<Faculty>> faculty;

    public FacultyOverviewViewModel(@NonNull Application application) {
        super(application);
    }

    void init(){
        faculty = FacultyRepository.getInstance(getApplication()).getAllFaculty();
    }

    LiveData<List<Faculty>> getFaculty() {
        return this.faculty;
    }
}
