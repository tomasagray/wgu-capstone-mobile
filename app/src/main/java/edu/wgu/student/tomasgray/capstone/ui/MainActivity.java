package edu.wgu.student.tomasgray.capstone.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.ui.coursework.OverviewFragment;
import edu.wgu.student.tomasgray.capstone.ui.faculty.FacultyOverviewFragment;
import edu.wgu.student.tomasgray.capstone.ui.user.UserInfoFragment;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "MainActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item ->
            {
                switch (item.getItemId()) {
                    case R.id.navigation_coursework:
                        openOverview();
                        return true;
                    case R.id.navigation_faculty:
                        openFaculty();
                        return true;
                    case R.id.navigation_user:
                        openUser();
                        return true;
                }
                return false;
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Default, open to Faculty Overview
        if(savedInstanceState == null) {
            openFaculty();
            navView.setSelectedItemId(R.id.navigation_faculty);
        }
    }

    /**
     * Switch between fragments (main menu items) using the main bottom navigation.
     *
     * @param fragment The fragment to switch to
     *
     */
    private void openFragment(Fragment fragment)
    {
        // Perform switch in a transaction
        final FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace( R.id.frame_container, fragment );
        transaction.addToBackStack(null);
        transaction.commit();   // make it so!
    }
    private void openOverview()
    {
        openFragment(new OverviewFragment());
        setTitle(
                getResources().getString(R.string.coursework_overview)
        );
        Log.i(LOG_TAG, "OverviewFragment clicked");
    }
    private void openFaculty()
    {
        Log.i(LOG_TAG, "Faculty clicked");
        openFragment(new FacultyOverviewFragment());
        setTitle(
                getResources().getString(R.string.faculty_overview)
        );
    }
    private void openUser()
    {
        Log.i(LOG_TAG, "User profile clicked");
        openFragment(new UserInfoFragment());
        setTitle(
                getResources().getString(R.string.user_profile)
        );
    }
}
