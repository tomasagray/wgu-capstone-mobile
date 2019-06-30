package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.data.access.AssessmentRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Assessment;

public class AssessmentDetailViewModel extends AndroidViewModel
{
    private LiveData<Assessment> assessment;

    public AssessmentDetailViewModel(@NonNull Application application) {
        super(application);
    }

    void init(UUID assessmentId)
    {
        assessment
                = AssessmentRepository
                    .getInstance(getApplication())
                    .getAssessment(assessmentId);
    }

    LiveData<Assessment> getAssessment() {
        return assessment;
    }
}
