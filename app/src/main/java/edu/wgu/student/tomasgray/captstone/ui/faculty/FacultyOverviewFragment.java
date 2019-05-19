package edu.wgu.student.tomasgray.captstone.ui.faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.student.tomasgray.captstone.R;

public class FacultyOverviewFragment extends Fragment {

    private FacultyOverviewViewModel facultyViewModel;
    private RecyclerView facultyList;

    public static FacultyOverviewFragment newInstance() {
        return new FacultyOverviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return
                inflater
                        .inflate(
                                R.layout.faculty_overview_fragment,
                                container,
                                false
                        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Setup faculty list RecyclerView
        facultyList = getActivity().findViewById(R.id.facultyList);
        facultyList.setLayoutManager( new LinearLayoutManager(getContext()));
        FacultyListAdapter facultyListAdapter = new FacultyListAdapter( getActivity() );
        facultyList.setAdapter(facultyListAdapter);

        // TODO: Use the ViewModel
        facultyViewModel = ViewModelProviders.of(this).get(FacultyOverviewViewModel.class);
        facultyViewModel.init();
        facultyViewModel.getFaculty().observe(this, facultyListAdapter::setData);

    }

}
