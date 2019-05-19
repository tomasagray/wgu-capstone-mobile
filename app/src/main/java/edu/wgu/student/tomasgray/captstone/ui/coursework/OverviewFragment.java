package edu.wgu.student.tomasgray.captstone.ui.coursework;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;

import edu.wgu.student.tomasgray.captstone.R;
import edu.wgu.student.tomasgray.captstone.data.access.TermRepository;
import edu.wgu.student.tomasgray.captstone.ui.views.ProgressButtonView;

public class OverviewFragment extends Fragment
    implements TermDetailFragment.OnFragmentInteractionListener
{
    private static final String LOG_TAG = "OverviewFrag";


    private OverviewViewModel viewModel;
    private TermDetailViewModel termDetailViewModel;
    private UUID termId;   // GUI
    // --------------------------------------------
    private TextView termLabel;
    private TextView daysLeft;
    private TextView startDate;
    private TextView endDate;
    private ProgressButtonView termProgressBar;
    private RecyclerView courseList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Get current term
        // TODO: GET THIS WORKING!
        Executors.newSingleThreadExecutor().execute(() -> {
            Cursor cursor = TermRepository.getInstance(getContext()).getCurrentTerm(Date.from(Instant.now()));
            if( cursor.getCount() > 0 ) {
                Log.i(LOG_TAG, "Current term: got results(#): " + cursor.getCount());
                cursor.moveToNext();
                Log.i(LOG_TAG, "termId for current term: " + cursor.getString(cursor.getColumnIndex("termId")));
                String id = cursor.getString(cursor.getColumnIndex("termId"));
                termId = UUID.fromString(id);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.i(LOG_TAG, "Overview Fragment context: " + getContext());

        // TODO: Fix this!
        // Get course ID
        if(savedInstanceState != null) {
            Log.i(LOG_TAG, "savedInstanceState was not null!" + savedInstanceState.toString());
        }

        return
                inflater.inflate(
                        R.layout.coursework_fragment,
                        container,
                        false
                );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if(fragment instanceof TermDetailFragment) {
            TermDetailFragment detailFragment = (TermDetailFragment)fragment;
            detailFragment.setFragmentInteractionListener(this);
        }
    }

    @Override
    public void onProgressButtonClick() {
        Log.i(LOG_TAG, "Click in overview");
        Intent intent = new Intent(getContext(), TermListActivity.class);
        startActivityForResult(intent, 0);
    }
}
