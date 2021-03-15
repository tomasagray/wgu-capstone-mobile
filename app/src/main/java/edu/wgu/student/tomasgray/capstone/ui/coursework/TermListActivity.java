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

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.capstone.data.model.Term;

public class TermListActivity extends AppCompatActivity
    implements TermDetailFragment.OnFragmentInteractionListener
{
    private static final String LOG_TAG = "TermListActivity";

    // GUI
    private TermListViewModel viewModel;
    private TermSlidePagerAdapter pagerAdapter;
    // Data
    private List<Term> termList;

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

        if(savedInstanceState != null) {
            Log.i(LOG_TAG, "TermListAct created with bundle!: " + savedInstanceState);
        } else {
            Log.i(LOG_TAG, "TermListAct created with no bundle :(");
        }

        // Setup toolbar
        setTitle(getResources().getString(R.string.term_detail));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Setup
        this.viewModel = ViewModelProviders.of(this).get(TermListViewModel.class);
        initializeGui();
        initializeViewModel();
    }

    private void initializeViewModel() {
//        if(viewModel != null)
//            return;

        // Attach ViewModel
        // -------------------------------------------------------------------
        viewModel.init(TermRepository.getInstance(getBaseContext()));
        // Observe data
        viewModel.getTermList().observe(this, terms -> {
            termList = terms;
            pagerAdapter.notifyDataSetChanged();
        });
    }

    private void initializeGui() {
        // Setup ViewPager
        // -------------------------------------------------------------------
        ViewPager termViewPager = findViewById(R.id.termViewPager);
        this.pagerAdapter = new TermSlidePagerAdapter(getSupportFragmentManager());
        termViewPager.setAdapter(pagerAdapter);
        termViewPager.setCurrentItem(0);
        termViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(LOG_TAG, "Position is: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Setup tabs
        TabLayout pagerTabs = findViewById(R.id.pagerTabs);
        pagerTabs.setupWithViewPager(termViewPager, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment)
    {
        // Attach to Fragment interaction events
        if(fragment instanceof TermDetailFragment) {
            TermDetailFragment detailFragment = (TermDetailFragment)fragment;
            detailFragment.setFragmentInteractionListener(this);
        }
    }

    @Override
    public void onProgressButtonClick() {
        // Empty method; take no action from this Activity
    }

    /**
     * Pager adapter class for ViewPager
     */
    private class TermSlidePagerAdapter extends FragmentPagerAdapter
    {
        public TermSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.i(LOG_TAG, "Creating fragment for term: " + termList.get(position) + ", position: " + position);
            return TermDetailFragment.newFragment( termList.get(position).getTermId() );
        }

        @Override
        public int getCount() {
            return (termList == null) ? 0 : termList.size();
        }
    }

}
