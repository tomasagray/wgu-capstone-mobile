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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Assessment;

public class AssessmentDetailActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "AssessDetailActivity";

    // Argument flag for Intents
    static final String ARG_ASSESSMENT_ID = "assessment_id";

    // GUI
    private TextView assessmentTitle;
    private TextView assessmentType;
    private RecyclerView itemsList;
    // ViewModel
    private AssessmentDetailViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Title
        setTitle(getResources().getString(R.string.assessment_details));

        // Attach GUI
        initializeGui();

        // Extract Assessment ID from Intent
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            UUID assessmentId = (UUID)extras.get(ARG_ASSESSMENT_ID);
            Log.i(LOG_TAG, "Initializing Activity with data for assessment: " + assessmentId);
            initializeViewModel(assessmentId);
        } else {
            Log.e(LOG_TAG, "Activity opened with no extras!");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeGui()
    {
        // Attach GUI references
        assessmentTitle = findViewById(R.id.assessmentTitleText);
        assessmentType = findViewById(R.id.typeText);
        itemsList = findViewById(R.id.assessmentItemList);
        // Setup RecyclerView for items
        AssessmentItemAdapter adapter = new AssessmentItemAdapter();
        itemsList.setAdapter(adapter);
        itemsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initializeViewModel(UUID assessmentId)
    {
        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(AssessmentDetailViewModel.class);
        viewModel.init(assessmentId);
        viewModel.getAssessment().observe(this, assessment -> {
            // Update top-level GUI
            assessmentTitle.setText(assessment.getTitle());
            assessmentType.setText(assessment.getType().name().toUpperCase());
            // Update RecyclerView adapter data
            ((AssessmentItemAdapter)itemsList.getAdapter())
                    .setData(assessment.getAssessmentItems());
        });
    }

    private class AssessmentItemAdapter extends RecyclerView.Adapter<AssessmentItemAdapter.ItemViewHolder>
    {
        private List<Assessment.AssessmentItem> items;

        void setData(List<Assessment.AssessmentItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view
                    = LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.assessment_item_fragment,
                                parent,
                                false
                        );

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
        {
            Assessment.AssessmentItem item = items.get(position);
            // Attach data to GUI components
            holder.itemTitle.setText(item.getTitle());
            holder.itemDescription.setText(item.getDescription());
            holder.competentText.setText(item.getCompetent());
            holder.approachingText.setText(item.getApproaching());
            holder.failText.setText(item.getIncompetent());
        }

        @Override
        public int getItemCount() {
            // Because we are dealing with LiveData, our data set
            // is initially null
            return (items == null) ? 0 : items.size();
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder
        {
            // Item GUI components
            TextView itemTitle;
            TextView itemDescription;
            TextView competentText;
            TextView approachingText;
            TextView failText;

            public ItemViewHolder(@NonNull View itemView)
            {
                super(itemView);
                // Attach GUI
                itemTitle = itemView.findViewById(R.id.itemTitleText);
                itemDescription = itemView.findViewById(R.id.itemDescriptionText);
                competentText = itemView.findViewById(R.id.competentItemText);
                approachingText = itemView.findViewById(R.id.approachingText);
                failText = itemView.findViewById(R.id.notCompetentText);
            }
        }
    }
}
