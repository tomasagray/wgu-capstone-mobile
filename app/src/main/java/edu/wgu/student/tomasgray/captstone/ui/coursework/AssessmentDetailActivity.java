package edu.wgu.student.tomasgray.captstone.ui.coursework;

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

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.UUID;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.model.Assessment;

public class AssessmentDetailActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "AssessDetailActivity";

    // Argument flag for Intents
    static final String ARG_ASSESSMENT_ID = "assessment_id";

    // GUI
    private TextView assessmentTitle;
    private TextView assessmentDate;
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
            Log.i(LOG_TAG, "Initializing Activity with data for assessment: " + assessmentId.toString());
            initializeViewModel(assessmentId);
        } else {
            Log.e(LOG_TAG, "Activity opened with no extras!");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        // Attach GUI references
        assessmentTitle = findViewById(R.id.assessmentTitleText);
        assessmentDate = findViewById(R.id.assessmentDateText);
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
            // Format date
            String date
                    = assessment
                        .getStartDate()
                        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
            // Update top-level GUI
            assessmentTitle.setText(assessment.getTitle());
            assessmentDate.setText(date);
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
            holder.competentText.setText(item.getCompetence());
            holder.approachingText.setText(item.getApproaching());
            holder.failText.setText(item.getIncompetence());
        }

        @Override
        public int getItemCount() {
            // Because we are dealing with LiveData, our dataset
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
