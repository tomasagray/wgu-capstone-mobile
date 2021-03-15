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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.ui.App;

public class OverviewFragment extends Fragment
        implements TermDetailFragment.OnFragmentInteractionListener {
    private static final String LOG_TAG = "OverviewFrag";

    private TermDetailViewModel detailViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        detailViewModel = ViewModelProviders.of(getActivity()).get(TermDetailViewModel.class);
//        detailViewModel.setCurrentTerm();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

        // Initialize GUI
        FragmentActivity activity = getActivity();
        if (activity != null) {
            TextView userNameText = activity.findViewById(R.id.userNameText);
            userNameText.setText(
                    App.getAuthorization().getStudent().getFirstName()
            );
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof TermDetailFragment) {
            TermDetailFragment detailFragment = (TermDetailFragment) fragment;
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
