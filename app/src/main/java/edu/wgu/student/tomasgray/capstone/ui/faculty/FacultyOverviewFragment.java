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

package edu.wgu.student.tomasgray.capstone.ui.faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.student.tomasgray.capstone.R;

public class FacultyOverviewFragment extends Fragment {

    private FacultyOverviewViewModel facultyViewModel;
    private RecyclerView facultyList;

    public static FacultyOverviewFragment newInstance() {
        return new FacultyOverviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return
                inflater
                        .inflate(
                                R.layout.faculty_overview_fragment,
                                container,
                                false
                        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Setup faculty list RecyclerView
        facultyList = getActivity().findViewById(R.id.facultyList);
        facultyList.setLayoutManager( new LinearLayoutManager(getContext()));
        FacultyListAdapter facultyListAdapter = new FacultyListAdapter( getActivity() );
        facultyList.setAdapter(facultyListAdapter);

        // TODO: Use the ViewModel
        facultyViewModel = ViewModelProviders.of(this).get(FacultyOverviewViewModel.class);
        facultyViewModel.init();
        facultyViewModel.getFaculty().observe(this, facultyListAdapter::setData);

    }

}
