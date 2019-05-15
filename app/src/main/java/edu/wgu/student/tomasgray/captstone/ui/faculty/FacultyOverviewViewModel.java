package edu.wgu.student.tomasgray.captstone.ui.faculty;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.data.access.FacultyRepository;
import edu.wgu.student.tomasgray.captstone.data.model.Faculty;

public class FacultyOverviewViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "FacultyOvVM";

    private LiveData<List<Faculty>> faculty;
    private FacultyRepository facultyRepository;

    public FacultyOverviewViewModel(@NonNull Application application) {
        super(application);
    }

    void init(){
        facultyRepository = FacultyRepository.getInstance(getApplication());
        faculty = facultyRepository.getAllFaculty();
        Log.i(LOG_TAG, "Init-ing done: " + faculty.getValue());
        Log.i(LOG_TAG, "Faculty test: " + facultyRepository.getFacultyMember(UUID.fromString("ca3cca99-41a5-4f7d-954a-227aafb8b682")).getValue());
    }

    LiveData<List<Faculty>> getFaculty() {
        return this.faculty;
    }
}
