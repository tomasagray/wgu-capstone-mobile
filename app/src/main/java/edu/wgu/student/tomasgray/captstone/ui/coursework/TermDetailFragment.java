package edu.wgu.student.tomasgray.captstone.ui.coursework;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.model.Course;
import edu.wgu.student.tomasgray.captstone.data.model.CourseUtils;
import edu.wgu.student.tomasgray.captstone.data.model.Term;
import edu.wgu.student.tomasgray.captstone.data.model.Topic;
import edu.wgu.student.tomasgray.captstone.ui.views.ProgressButtonView;
import edu.wgu.student.tomasgray.captstone.ui.views.TopicButtonAdapter;


public class TermDetailFragment extends Fragment
    implements TopicButtonAdapter.OnAdapterInteractionListener
{
    private static final String LOG_TAG = "TermDetailFrag";
    // Passed in to identify the specific Term this
    // fragment represents
    private static final String ARG_TERM_ID = "termId";

    // Interaction listener
    private OnFragmentInteractionListener callback;

    // Data
    // ---------------------------------------------
    private UUID termId;
    private List<Course> courseList;
    // ViewModel
    private TermDetailViewModel viewModel;

    // GUI
    // --------------------------------------------
    private TextView termLabel;
    private TextView daysLeft;
    private TextView startDate;
    private TextView endDate;
    private ProgressButtonView termProgressBar;
    private RecyclerView courseListRecycler;



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


    public void setFragmentInteractionListener(OnFragmentInteractionListener callback) {
        this.callback = callback;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProgressButtonClick();
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        if(getArguments() != null) {
            // Get the term ID passed in
            Log.i(LOG_TAG, "getArguments not null: " + getArguments().getString(ARG_TERM_ID));
            termId = UUID.fromString( getArguments().getString(ARG_TERM_ID) );
        } else {
            Log.i(LOG_TAG, "Fragment was created with null arguments");
//            termId = UUID.fromString("34a643a5-d62a-432e-a09d-c2a19e0c63b1");
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
        Log.i(LOG_TAG, "Termdetailfrag activity created");
        initializeGui();
        initializeViewModel();
    }


    /**
     * For course list recycler
     * @param position Which item in the RecyclerView was clicked
     */
    @Override
    public void onItemClick(int position) {
        // Get the Course from the List
        Course course = courseList.get(position);
        // Setup intent
        Intent intent = new Intent(getContext(), CourseDetailActivity.class);
        intent.putExtra(CourseDetailActivity.ARG_COURSE_ID, course.getId().toString());
        // Start activity
        startActivityForResult(intent, 0);
    }


    private void initializeGui()
    {
        // Attach GUI components
        termLabel = getView().findViewById(R.id.termLabel);
        daysLeft  = getView().findViewById(R.id.daysLeftCounterText);
        startDate = getView().findViewById(R.id.termStartDateText);
        endDate   = getView().findViewById(R.id.termEndDateText);
        termProgressBar = getView().findViewById(R.id.termProgressButton);
        termProgressBar.setOnClickListener(v -> callback.onProgressButtonClick());
        // Setup course list
        courseListRecycler = getView().findViewById(R.id.courseListRecycler);
        TopicButtonAdapter adapter = new TopicButtonAdapter();
        adapter.setAdapterInteractionListener(this);
        courseListRecycler.setAdapter( adapter );
        courseListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initializeViewModel()
    {
        // Initialize ViewModel
        // TODO: FIX THIS!
        if(termId != null) {
            Log.i(LOG_TAG, "Term ID is not null! ID: " + termId.toString());
            viewModel = ViewModelProviders.of(this).get(termId.toString(), TermDetailViewModel.class);
            viewModel.init(termId);
        } else {
            Log.i(LOG_TAG, "termId is still null!");
            viewModel = ViewModelProviders.of(this).get(TermDetailViewModel.class);
//            viewModel.init(UUID.fromString("34a643a5-d62a-432e-a09d-c2a19e0c63b1"));
        }

        // Observe data
        LiveData<Term> term = viewModel.getTerm();
        if(term != null) {
            term.observe(this, termData -> {
                // Format data
                String days = termData.getDaysLeft() + "";
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.US);
                String start = formatter.format(termData.getStartDate());
                String end = formatter.format(termData.getEndDate());

                // Attach data to GUI
                termLabel.setText(termData.getTitle());
                daysLeft.setText(days);
                startDate.setText(start);
                endDate.setText(end);
//                termProgressBar.setPercentage();

                // Save term ID
                termId = termData.getTermId();
            });
        }

        LiveData<List<Course>> courses = viewModel.getCourses();
        if(courses != null) {
            courses.observe(this, cl -> {
                // TODO: More stuff
                // Update courses list
                courseList = cl;
                // Convert to List of Topics
                List<Topic> topicList
                        = cl.stream()
                            .map(CourseUtils::convertToTopic)
                            .collect(Collectors.toList());
                ((TopicButtonAdapter) courseListRecycler.getAdapter())
                        .setData(topicList);
            });
        }
    }
}
