package edu.wgu.student.tomasgray.capstone.ui.faculty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

public class FacultyDetailsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "FacultyDeetsActv";
    // For intent bundles
    public static final String ARG_FACULTY_ID = "faculty_id";
    // For for calls
    public static final int CALL_PERMISSIONS_REQUEST = 5432;

    // GUI
    private ImageView avatar;
    private TextView facultyNameText;
    private Button emailButton;
    private Button phoneButton;
    // Office hours
    private TextView monOfficeHours;
    private TextView tuesOfficeHours;
    private TextView wedsOfficeHours;
    private TextView thursOfficeHours;
    private TextView friOfficeHours;

    // VM
    private FacultyDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);

        // Setup toolbar
        setTitle(getResources().getString(R.string.faculty_details));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Attach GUI
        initializeGui();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Log.i(LOG_TAG, "Intent passed with extras: "  + extras.get(ARG_FACULTY_ID));
            // Initialize ViewModel
            initializeViewModel( (UUID)extras.get(ARG_FACULTY_ID) );
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeGui()
    {
        final String officeHours = "8 am — 5 pm";

        avatar = findViewById(R.id.facultyAvatar);
        facultyNameText = findViewById(R.id.facultyNameText);
        emailButton = findViewById(R.id.emailFacultyButton);
        phoneButton = findViewById(R.id.callFacultyButton);
        //Button handlers
        emailButton.setOnClickListener( v -> {
            if(viewModel.getFacultyLiveData().getValue() != null) {
                Faculty faculty = viewModel.getFacultyLiveData().getValue();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, faculty.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Schedule appointment");
                startActivity(intent);
            }
        });
        phoneButton.setOnClickListener( v -> {
            if(viewModel.getFacultyLiveData().getValue() != null) {
                Faculty faculty = viewModel.getFacultyLiveData().getValue();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + faculty.getPhone()));
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat
                            .requestPermissions(
                                    this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    CALL_PERMISSIONS_REQUEST
                            );
                } else {
                    try {
                        startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        // Office hours
        monOfficeHours = findViewById(R.id.monOfficeHoursText);
        tuesOfficeHours = findViewById(R.id.tuesOfficeHoursText);
        wedsOfficeHours = findViewById(R.id.wedsOfficeHoursText);
        thursOfficeHours = findViewById(R.id.thursOfficeHoursText);
        friOfficeHours = findViewById(R.id.friOfficeHoursText);
        // Set office hours
        monOfficeHours.setText(officeHours);
        tuesOfficeHours.setText(officeHours);
        wedsOfficeHours.setText(officeHours);
        thursOfficeHours.setText(officeHours);
        friOfficeHours.setText(officeHours);
    }

    private void initializeViewModel(UUID facultyId)
    {
        viewModel = ViewModelProviders.of(this).get(FacultyDetailsViewModel.class);
        viewModel.init(facultyId);

        // Attach data to GUI
        if(viewModel.getFacultyLiveData() != null) {
            viewModel.getFacultyLiveData().observe(this, faculty -> {
                facultyNameText.setText(faculty.getFullName());
                emailButton.setText(faculty.getEmail());
                phoneButton.setText(faculty.getPhoneFormatted());
            });
        }
    }
}
