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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Course;
import edu.wgu.student.tomasgray.capstone.data.model.CourseUtils;
import edu.wgu.student.tomasgray.capstone.data.model.Term;
import edu.wgu.student.tomasgray.capstone.data.model.Topic;
import edu.wgu.student.tomasgray.capstone.ui.views.ProgressButtonView;
import edu.wgu.student.tomasgray.capstone.ui.views.TopicButtonAdapter;


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
//    private TextView termLabel;
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

        // Initialize ViewModel
        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(TermDetailViewModel.class);
        }

        if(getArguments() != null) {
            // Get the term ID passed in
            termId = UUID.fromString( getArguments().getString(ARG_TERM_ID) );
            Log.i(LOG_TAG, "Fragment created with term ID: " + termId);
            viewModel.init(termId);
        } else {
            Log.i(LOG_TAG, "Fragment was created with null arguments");
            viewModel.setCurrentTerm();
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
//        termLabel = getView().findViewById(R.id.termLabel);
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
        // Observe data
        viewModel.getTerm().observe(this, termData -> {
                if(termData == null)
                    return;
                
                // Format data
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                String days = (termData.getDaysLeft() > 0) ? termData.getDaysLeft() + "" : "0";
                String start = termData.getStartDate().format(formatter);
                String end = termData.getEndDate().format(formatter);
                int percent = calcTermPercent(termData);

                // Attach data to GUI
//                termLabel.setText(termData.getTermId().toString());
                daysLeft.setText(days);
                startDate.setText(start);
                endDate.setText(end);
                // TODO: Implement this!
                termProgressBar.setPercentage(percent);
                // Set courses
                courseList = termData.getCourseList();
                Log.i(LOG_TAG, "COurselist IS: " + courseList);

                if(courseList != null) {
                    Log.i(LOG_TAG, "CourseList for term: " + termData.getTermId() + " has " + termData.getCourseList().size() + " entries");
                    List<Topic> topicList =
                            courseList
                                .stream()
                                .map(CourseUtils::convertToTopic)
                                .collect(Collectors.toList());

                    Log.i(LOG_TAG, "courselist SO WHY ARENT WE HERE? # = " + topicList.size());
                    ((TopicButtonAdapter) courseListRecycler.getAdapter())
                            .setData(topicList);
                } else {
                    Log.i(LOG_TAG, "CourseList is null for term: " + termData.getTermId());
                }
        });
    }

    protected void setTermId(UUID termId) {
        this.termId = termId;
        viewModel.init(termId);
    }

    private int calcTermPercent(Term term)
    {
        // Calculate term length
        long termLength = ChronoUnit.DAYS.between( term.getStartDate(), term.getEndDate() );
        // Calculate days remaining; min 0
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), term.getEndDate());
        daysLeft = (daysLeft < 0) ? 0 : daysLeft;
        // Calculate percent days left represents
        float pct = daysLeft / (float)termLength;
        // Percent-ify, round, subtract from 100
        return 100 - Math.round(100 *pct);
    }
}
