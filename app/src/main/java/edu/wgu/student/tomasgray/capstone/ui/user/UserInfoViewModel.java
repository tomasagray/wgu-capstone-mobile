package edu.wgu.student.tomasgray.capstone.ui.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.wgu.student.tomasgray.capstone.data.access.StudentRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Student;

public class UserInfoViewModel extends AndroidViewModel
{
    // Repos
    private StudentRepository studentRepository;
    private LiveData<Student> studentLiveData;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);

        this.studentRepository = StudentRepository.getInstance(application);
        this.studentLiveData = studentRepository.getStudentData();
    }
    // TODO: Implement the ViewModel

    public LiveData<Student> getStudentLiveData() {
        return studentLiveData;
    }
    void clearStudentData() {
        studentRepository.clearStudentData();
    }
}
