package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.ui.App;

public class OverviewFragment extends Fragment
    implements TermDetailFragment.OnFragmentInteractionListener
{
    private static final String LOG_TAG = "OverviewFrag";

    private TextView userNameText;
    private TermDetailViewModel detailViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        detailViewModel = ViewModelProviders.of(getActivity()).get(TermDetailViewModel.class);
//        detailViewModel.setCurrentTerm();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return
                inflater.inflate(
                        R.layout.coursework_fragment,
                        container,
                        false
                );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize GUI
        userNameText = getActivity().findViewById(R.id.userNameText);

        userNameText.setText(
                App.getAuthorization().getStudent().getFirstName()
        );
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if(fragment instanceof TermDetailFragment) {
            TermDetailFragment detailFragment = (TermDetailFragment)fragment;
            detailFragment.setFragmentInteractionListener(this);
        }
    }

    @Override
    public void onProgressButtonClick() {
        Log.i(LOG_TAG, "Click in overview");
        Intent intent = new Intent(getContext(), TermListActivity.class);
        startActivityForResult(intent, 0);
    }
}
