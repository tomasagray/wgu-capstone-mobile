package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import edu.wgu.student.tomasgray.captstone.R;

public class AssessmentItemFragment extends Fragment {

    private AssessmentDetailViewModel mViewModel;

    public static AssessmentItemFragment newInstance() {
        return new AssessmentItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.assessment_item_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AssessmentDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
