package edu.wgu.student.tomasgray.captstone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.ui.coursework.OverviewFragment;
import edu.wgu.student.tomasgray.captstone.ui.coursework.TermDetailFragment;
import edu.wgu.student.tomasgray.captstone.ui.coursework.TermListActivity;
import edu.wgu.student.tomasgray.captstone.ui.faculty.FacultyOverviewFragment;

public class MainActivity extends AppCompatActivity
        implements TermDetailFragment.OnFragmentInteractionListener
{
    private static final String LOG_TAG = "MainActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item ->
            {
                switch (item.getItemId()) {
                    case R.id.navigation_coursework:
                        openFragment(new OverviewFragment());
                        setTitle(
                                getResources().getString(R.string.coursework_overview)
                        );
                        Log.i(LOG_TAG, "OverviewFragment clicked");
                        return true;
                    case R.id.navigation_faculty:
                        Log.i(LOG_TAG, "Faculty clicked");
                        openFragment(new FacultyOverviewFragment());
                        setTitle(
                                getResources().getString(R.string.faculty_overview)
                        );
                        return true;
                    case R.id.navigation_user:
                        Log.i(LOG_TAG, "User profile clicked");
                        setTitle(
                                getResources().getString(R.string.user_profile)
                        );
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

    @Override
    public void onProgressButtonClick(@Nullable UUID termId) {
        Log.i(LOG_TAG, "Progress button clicked");

        if(termId != null) {
            Intent intent = new Intent(this, TermListActivity.class);
            startActivityForResult(intent, 0);
        }
    }
}
