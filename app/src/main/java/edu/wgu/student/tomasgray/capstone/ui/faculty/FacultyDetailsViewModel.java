package edu.wgu.student.tomasgray.capstone.ui.faculty;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.FacultyRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

public class FacultyDetailsViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = "FacultyDetailsVM";

    private LiveData<Faculty> facultyLiveData;

    public FacultyDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Faculty> getFacultyLiveData() {
        return facultyLiveData;
    }

    public void init(UUID facultyId)
    {
        Log.i(LOG_TAG, "Initializing view model with ID: " + facultyId.toString());
        facultyLiveData
                = FacultyRepository
                    .getInstance(getApplication())
                    .getFacultyMember(facultyId);
    }
}
