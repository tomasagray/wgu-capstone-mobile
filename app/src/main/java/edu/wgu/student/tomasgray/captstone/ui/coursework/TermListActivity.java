package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;

public class TermListActivity extends AppCompatActivity
    implements TermDetailFragment.OnFragmentInteractionListener
{
    private static final String LOG_TAG = "TermDetailActiv";

    private TermListViewModel viewModel;
    private ViewPager termViewPager;
    private TermSlidePagerAdapter pagerAdapter;
    private int termCount;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(LOG_TAG, "Back button pressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Setup toolbar
        setTitle(getResources().getString(R.string.term_detail));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        // Attach ViewModel
        this.viewModel = ViewModelProviders.of(this).get(TermListViewModel.class);
        viewModel.init(TermRepository.getInstance(getBaseContext()));

        // Observe data
        viewModel.getTermList().observe(this, terms -> {
            // TODO: Get some data!
            Log.i(LOG_TAG, "Term count: " + terms.size());
            termCount = terms.size();
            pagerAdapter.notifyDataSetChanged();
        });

        // Setup ViewPager
        this.termViewPager = findViewById(R.id.termViewPager);
        this.pagerAdapter = new TermSlidePagerAdapter(getSupportFragmentManager());
        termViewPager.setAdapter(pagerAdapter);
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

    @Override
    public void onProgressButtonClick(UUID termId) {
        Log.i(LOG_TAG, "Progress button clicked, ID: " + termId.toString());
    }


    /**
     * Pager adapter class for ViewPager
     */
    private class TermSlidePagerAdapter extends FragmentPagerAdapter
    {
        public TermSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TermDetailFragment();
        }

        @Override
        public int getCount() {
            return termCount;
        }
    }

}
