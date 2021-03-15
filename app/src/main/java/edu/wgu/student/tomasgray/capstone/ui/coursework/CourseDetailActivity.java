/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.ui.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Assessment;
import edu.wgu.student.tomasgray.capstone.data.model.AssessmentUtils;
import edu.wgu.student.tomasgray.capstone.data.model.CourseUtils;
import edu.wgu.student.tomasgray.capstone.data.model.Note;
import edu.wgu.student.tomasgray.capstone.data.model.Topic;
import edu.wgu.student.tomasgray.capstone.ui.views.NoteSlidePagerAdapter;
import edu.wgu.student.tomasgray.capstone.ui.views.ProgressButtonView;
import edu.wgu.student.tomasgray.capstone.ui.views.TopicButtonAdapter;

import static edu.wgu.student.tomasgray.capstone.ui.coursework.AssessmentDetailActivity.ARG_ASSESSMENT_ID;
import static edu.wgu.student.tomasgray.capstone.ui.coursework.NoteFragment.ARG_NOTE_ID;

public class CourseDetailActivity extends AppCompatActivity
    implements // interfaces
        TopicButtonAdapter.OnAdapterInteractionListener,
        NoteSlidePagerAdapter.OnSliderClickListener
{
    private static final String LOG_TAG = "CourseDetailAct";

    public static final String ARG_COURSE_ID = "course_id";
    // Note editor request codes
    private static final int NEW_NOTE = 1;
    private static final int EDIT_NOTE = 2;

    // Data
    // -----------------------------------------------------
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

    // Mentor
    private LinearLayout mentorCard;
    private TextView mentorName;
    private TextView mentorPhone;
    private TextView mentorEmail;
    private ProgressButtonView courseProgressBar;
    private RecyclerView assessmentList;
//    private Button addNoteButton;
    // Notes
    private ViewPager noteViewPager;
    private NoteSlidePagerAdapter noteSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Title
        setTitle(
                getResources().getString(R.string.course_details)
        );

        initializeGui();
        viewModel
                = ViewModelProviders
                .of(this)
                .get(CourseDetailViewModel.class);

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
        Log.i(LOG_TAG, "1. Initializing VM for coursedeet with ID: "+  courseId);
        viewModel.init( UUID.fromString(courseId) );

        // Course data
        // ------------------------------------------------------------------
        viewModel.getCourse().observe(this, course -> {
            if(course == null) {
                return;
            }

            Log.i(LOG_TAG, "2. Initializing VM for coursedeet with ID: "+  courseId);
            // Format data
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd");
            String start = course.getStartDate().format(dateTimeFormatter);
            String end = course.getEndDate().format(dateTimeFormatter);
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

            // Mentor
            if(course.getMentor() != null) {
                Log.i(LOG_TAG, "Course has a coursementor! " + course.getMentor());
                mentorName.setText(course.getMentor().getFullName());
                mentorEmail.setText(course.getMentor().getEmail());
                mentorPhone.setText(course.getMentor().getPhoneFormatted());
            } else {
                mentorCard.setVisibility(View.GONE);
            }
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
            TopicButtonAdapter adapter = (TopicButtonAdapter) (assessmentList.getAdapter());
            if (adapter != null) {
                adapter.setData(topics);
            }
        });

        // Notes
        // -------------------------------------------------------------
        viewModel.getNotes().observe(this, notes -> noteSlidePagerAdapter.setData(notes));
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
        mentorCard = findViewById(R.id.facultyCard);
        ImageView mentorImage = findViewById(R.id.facultyImage);
        mentorName = findViewById(R.id.facultyName);
        mentorPhone = findViewById(R.id.facultyPhone);
        mentorEmail = findViewById(R.id.facultyEmail);

        // Assessments
        assessmentList = findViewById(R.id.assessmentList);
        TopicButtonAdapter adapter = new TopicButtonAdapter();
        adapter.setAdapterInteractionListener(this);
        assessmentList.setAdapter(adapter);
        assessmentList.setLayoutManager( new LinearLayoutManager(getApplicationContext()) );

        // Notes
        // ----------------------------------------------
        // Add button
        // TODO: Implement notes
//        addNoteButton = findViewById(R.id.addNoteButton);
//        addNoteButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this, NoteEditorActivity.class);
//            startActivityForResult(intent, NEW_NOTE);
//        });
        // ViewPager
        noteViewPager = findViewById(R.id.noteViewPager);
        noteSlidePagerAdapter = new NoteSlidePagerAdapter(getSupportFragmentManager());
        noteSlidePagerAdapter.setOnSliderClickListener(this);
        noteViewPager.setAdapter(noteSlidePagerAdapter);
        // Pager tabs
        TabLayout notePagerTabs = findViewById(R.id.notePagerTabs);
        notePagerTabs.setupWithViewPager(noteViewPager);
    }

    @Override
    public void onItemClick(int position) {
        Log.i(LOG_TAG, "Assessment list clicked, position: " + position);
        List<Assessment> assessments = viewModel.getAssessments().getValue();
        if(assessments != null) {
            // Get the assessment we're after from the list
            Assessment assessment = assessments.get(position);
            Log.i(LOG_TAG, "Starting Activity for assessment: " + assessment);
            Intent intent = new Intent(this, AssessmentDetailActivity.class);
            intent.putExtra(ARG_ASSESSMENT_ID, assessment.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onSlideClick() {
        Log.i(LOG_TAG, "Note slide clicked: " + noteViewPager.getCurrentItem());

        // TODO: Open note editor with this note
        try {
            // Determine which note was clicked
            int position = noteViewPager.getCurrentItem();
            // Get a note from the list
            List<Note> notes = viewModel
                    .getNotes()
                    .getValue();
            if (notes != null) {
                Note note = notes.get(position);
                Intent intent = new Intent(this, NoteEditorActivity.class);
                intent.putExtra(ARG_NOTE_ID, note.getNoteId());
                startActivityForResult(intent, EDIT_NOTE);
            }

        } catch (NullPointerException e) {
            Log.e(
                    LOG_TAG,
                    "Error open note editor: could not find note in list!"
                    + "\n\tMessage: " + e.getMessage()
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Determine request
        switch (requestCode) {
            case NEW_NOTE:
                if(resultCode == RESULT_OK) {
                    // Add new note
                    Log.i(LOG_TAG, "New note was saved successfully");
                    noteSlidePagerAdapter.notifyDataSetChanged();
                } else {
                    Log.i(LOG_TAG, "Result was: " + resultCode);
                }
                break;

            case EDIT_NOTE:
                if(resultCode == RESULT_OK) {
                    // Update/overwrite note
                    Log.i(LOG_TAG, "Note successfully edited");
                    noteSlidePagerAdapter.notifyDataSetChanged();
                } else {
                    Log.i(LOG_TAG, "Result was: " + resultCode);
                }
                break;

            default:
                Log.e(LOG_TAG, "Unknown request code: " + requestCode);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
