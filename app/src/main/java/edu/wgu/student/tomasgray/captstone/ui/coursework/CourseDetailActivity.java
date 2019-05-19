package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.model.AssessmentUtils;
import edu.wgu.student.tomasgray.captstone.data.model.CourseUtils;
import edu.wgu.student.tomasgray.captstone.data.model.Topic;
import edu.wgu.student.tomasgray.captstone.ui.views.ProgressButtonView;
import edu.wgu.student.tomasgray.captstone.ui.views.TopicButtonAdapter;

public class CourseDetailActivity extends AppCompatActivity
    implements TopicButtonAdapter.OnAdapterInteractionListener
{
    private static final String LOG_TAG = "CourseDetailAct";

    public static final String ARG_COURSE_ID = "course_id";

    // ViewModel
    private CourseDetailViewModel viewModel;
    // GUI
    // ----------------------------------------------------
    private TextView courseName;
    private TextView courseNumber;
    private TextView courseCredits;
    private TextView daysLeft;
    private TextView startDate;
    private TextView endDate;
    private ImageView mentorImage;
    private TextView mentorName;
    private TextView mentorPhone;
    private TextView mentorEmail;
    private ProgressButtonView courseProgressBar;
    private RecyclerView assessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Title
        setTitle(
                getResources().getString(R.string.course_details)
        );

        initializeGui();

        // Unpack extras
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String courseId = extras.getString(ARG_COURSE_ID);
            Log.i(LOG_TAG, "Course ID is: " + courseId);
            initializeViewModel(courseId);
        }
    }

    private void initializeViewModel(String courseId)
    {
        // Setup ViewModel
        // -------------------------------------------------------------------
        viewModel
                = ViewModelProviders
                    .of(this)
                    .get(CourseDetailViewModel.class);
        viewModel.init( UUID.fromString(courseId) );
        // Course data
        // ------------------------------------------------------------------
        viewModel.getCourse().observe(this, course -> {
            // Format data
            String start = CourseUtils.getShortDate(course.getStartDate());
            String end = CourseUtils.getShortDate(course.getEndDate());
            String credits = CourseUtils.getCreditString(course);
            String days = CourseUtils.getDaysLeftString(course);
            int percent = CourseUtils.getPercentComplete(course);

            // Update GUI
            courseName.setText( course.getTitle() );
            courseNumber.setText( course.getCourseNumber() );
            courseCredits.setText(credits);
            daysLeft.setText(days);
            startDate.setText(start);
            endDate.setText(end);
            courseProgressBar.setPercentage(percent);
        });
        // Mentor
        // ----------------------------------------------------------------
        viewModel.getMentor().observe(this, mentor -> {
            // TODO: Actually set image
//            mentorImage.setImageBitmap();
            mentorName.setText(mentor.getFullName());
            mentorPhone.setText(mentor.getPhoneFormatted());
            mentorEmail.setText(mentor.getEmail());
        });
        // Assessments
        // ---------------------------------------------------------------
        viewModel.getAssessments().observe(this, assessments -> {
            // Convert Assessment list to Topic list
            List<Topic> topics
                    = assessments
                        .stream()
                        .map(AssessmentUtils::convertToTopic)
                        .collect(Collectors.toList());
            // Attach data to RecyclerView
            ((TopicButtonAdapter)(assessmentList.getAdapter()))
                    .setData(topics);
        });
    }

    private void initializeGui()
    {
        // GUI references
        courseName = findViewById(R.id.courseNameText);
        courseNumber = findViewById(R.id.courseNumberText);
        courseCredits = findViewById(R.id.creditsText);
        daysLeft = findViewById(R.id.daysLeftCounterText);
        startDate = findViewById(R.id.courseStartDateText);
        endDate = findViewById(R.id.courseEndDateText);
        courseProgressBar = findViewById(R.id.courseProgressBar);

        // Mentor
        mentorImage = findViewById(R.id.facultyImage);
        mentorName = findViewById(R.id.facultyName);
        mentorPhone = findViewById(R.id.facultyPhone);
        mentorEmail = findViewById(R.id.facultyEmail);

        // Assessments
        assessmentList = findViewById(R.id.assessmentList);
        TopicButtonAdapter adapter = new TopicButtonAdapter();
        adapter.setAdapterInteractionListener(this);
        assessmentList.setAdapter(adapter);
        assessmentList.setLayoutManager( new LinearLayoutManager(getApplicationContext()) );
    }

    @Override
    public void onItemClick(int position) {
        Log.i(LOG_TAG, "Assessment list clicked, position: " + position);
    }
}
