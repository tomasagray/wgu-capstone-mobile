package edu.wgu.student.tomasgray.captstone.ui.faculty;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.wgu.student.tomasgray.captstone.R;

public class FacultyDetailsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "FacultyDeetsActv";
    // For intent bundles
    public static final String ARG_FACULTY_ID = "faculty_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);

        // Setup toolbar
        setTitle(getResources().getString(R.string.faculty_details));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null) {
            Log.i(LOG_TAG, "bundle was: " + savedInstanceState.toString());
        } else {
            Log.i(LOG_TAG, "bundle was null");
        }
    }

}
