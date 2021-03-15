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

package edu.wgu.student.tomasgray.capstone.ui.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.ui.App;
import edu.wgu.student.tomasgray.capstone.ui.login.LoginActivity;

public class UserInfoFragment extends Fragment
{
    private static final String LOG_TAG = "UserInfoFrag";

    // ViewModel
    private UserInfoViewModel viewModel;

    // GUI
    private TextView studentNameText;
    private TextView studentIdText;
    private TextView studentEmailText;
    private TextView studentPhoneText;
    // address
    private TextView addressText;
    private TextView cityText;
    private TextView stateText;
    private TextView zipText;

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.user_info_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize
        initializeGui();
        initializeViewModel();
    }

    private void initializeGui()
    {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.studentNameText = activity.findViewById(R.id.studentNameText);
            this.studentIdText = activity.findViewById(R.id.studentIdText);
            this.studentEmailText = activity.findViewById(R.id.studentEmailText);
            this.studentPhoneText = activity.findViewById(R.id.studentPhoneText);

            // Address
            this.addressText = activity.findViewById(R.id.addressText);
            this.cityText = activity.findViewById(R.id.cityText);
            this.stateText = activity.findViewById(R.id.stateText);
            this.zipText = activity.findViewById(R.id.zipText);
        }
    }

    private void initializeViewModel() {
        // Initialize ViewModel
        FragmentActivity activity = getActivity();
        if (activity != null) {
            viewModel = ViewModelProviders.of(activity).get(UserInfoViewModel.class);
            viewModel.getStudentLiveData().observe(getViewLifecycleOwner(), studentData -> {
                if(studentData == null)
                    return;

                studentNameText.setText(studentData.getFullName());
                studentIdText.setText(studentData.getStudentId().toString());
                studentEmailText.setText(studentData.getEmail());
                studentPhoneText.setText(studentData.getPhoneFormatted());

                // Address
                addressText.setText(studentData.getAddress());
                cityText.setText(studentData.getCity());
                stateText.setText(studentData.getState());
                this.zipText.setText(studentData.getZip());
            });
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        Log.i(LOG_TAG, "options menu being created");
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_edit_user:
                Intent intent = new Intent(getContext(), EditUserActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.action_logout:
                showLogoutConfirmDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLogoutConfirmDialog()
    {
        // Create confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage("LOGOUT:\nAre you sure?")
                .setPositiveButton(R.string.ok, (dialog, which) -> handleLogout())
                .setNegativeButton(R.string.cancel, (dialog, which) -> Log.d(LOG_TAG, "User DOES NOT want to logout"));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handleLogout() {
        // Logout
        Log.i(LOG_TAG, "User wants to logout");
//        ((App) getActivity().getApplication()).setAuthorization(null);
        App.setAuthorization(null);
        viewModel.clearStudentData();
        // End this activity
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
            // Start login activity
            startActivity(
                    new Intent( activity.getApplicationContext(), LoginActivity.class )
            );
        }
    }
}
