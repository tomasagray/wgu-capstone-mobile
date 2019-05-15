package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.Term;
import edu.wgu.student.tomasgray.captstone.ui.views.ProgressButtonView;


public class TermDetailFragment extends Fragment
{
    private static final String LOG_TAG = "TermDetailFrag";
    // Passed in to identify the specific Term this
    // fragment represents
    private static final String ARG_TERM_ID = "termId";

    // Data
    // ---------------------------------------------
    private UUID termId;
    // ViewModel
    private TermDetailViewModel viewModel;

    // GUI
    // --------------------------------------------
    private TextView termLabel;
    private TextView daysLeft;
    private TextView startDate;
    private TextView endDate;
    private ProgressButtonView termProgressBar;
    // GUI Listener
    private OnFragmentInteractionListener listener;



    /**
     * Factory method to create Term Detail fragments
     *
     * @param termId The ID of the Term this fragment will represent (UUID)
     * @return A TermDetailFragment for this Term
     */
    public static TermDetailFragment newFragment(@NonNull UUID termId)
    {
        TermDetailFragment fragment = new TermDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TERM_ID, termId.toString());
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            // Get the term ID passed in
            Log.i(LOG_TAG, "getArguments not null: " + getArguments().getString(ARG_TERM_ID));
            termId = UUID.fromString( getArguments().getString(ARG_TERM_ID) );
        } else {
            Log.i(LOG_TAG, "Fragment was created with null arguments");
            termId = UUID.fromString("34a643a5-d62a-432e-a09d-c2a19e0c63b1");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return
                inflater
                        .inflate(
                                R.layout.fragment_term_detail,
                                container,
                                false
                        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        // TODO: Get from container
        viewModel.init( termId );

        // Attach GUI components
        termLabel = getActivity().findViewById(R.id.termLabel);
        daysLeft  = getActivity().findViewById(R.id.daysLeftCounterText);
        startDate = getActivity().findViewById(R.id.termStartDateText);
        endDate   = getActivity().findViewById(R.id.termEndDateText);
        termProgressBar = getActivity().findViewById(R.id.termProgressButton);
        // Handle progress bar interaction
        termProgressBar.setOnClickListener(v -> {
            if(listener != null)
                listener.onProgressButtonClick(termId);
        });

        // Observe data
        LiveData<Term> term = viewModel.getTerm();
        if(term.getValue() != null) {
            term.observe(this, t -> {
                // Format data
                String days = t.getDaysLeft() + "";
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.US);
                String start = formatter.format(t.getStartDate());
                String end = formatter.format(t.getEndDate());

                // Attach data to GUI
                termLabel.setText(t.getTitle());
                daysLeft.setText(days);
                startDate.setText(start);
                endDate.setText(end);

                // Save term ID
                termId = t.getTermId();
            });
        } else {
            Log.i(LOG_TAG, "Term LiveData was null!");
        }

        LiveData<List<Course>> courses = viewModel.getCourses();
        if(courses != null) {
            courses.observe(this, courseList -> {
                // TODO: More stuff
                Log.i(LOG_TAG, "courseList is: " + courseList);
            });
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(LOG_TAG, "Context: " + context);

        // Ensure interaction interface implemented in activity
        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString()
                    + " must implement OnFragmentInteractionListener"
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Release listener
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProgressButtonClick(UUID termId);
    }
}
