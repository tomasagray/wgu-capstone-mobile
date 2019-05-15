package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;

public class OverviewFragment extends Fragment
{
    private static final String LOG_TAG = "OverviewFrag";


    private OverviewViewModel viewModel;
    private TermDetailViewModel termDetailViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.i(LOG_TAG, "Overview Fragment context: " + getContext());

        // TODO: Fix this!
        // Get course ID
        if(savedInstanceState != null) {
            Log.i(LOG_TAG, "savedInstanceState was not null!" + savedInstanceState.toString());
        }

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

        // Initialize the ViewModels
        viewModel = ViewModelProviders.of(getActivity()).get(OverviewViewModel.class);
        viewModel.init();
        termDetailViewModel = ViewModelProviders.of(getActivity()).get(TermDetailViewModel.class);

        // Get current course
        Executors.newSingleThreadExecutor().execute(() -> {
            Cursor termId = TermRepository.getInstance(getContext()).getCurrentTerm(LocalDate.now());
            if( termId.getCount() > 0 ) {
                Log.i(LOG_TAG, Arrays.toString(termId.getColumnNames()));
                Log.i(LOG_TAG, termId.getColumnCount() + "");
                termId.moveToNext();
                Log.i(LOG_TAG, "termId for current term: " + termId.getString(termId.getColumnIndex("termId")));
            }
        });
    }
}
