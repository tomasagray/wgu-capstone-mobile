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

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Faculty;

import static edu.wgu.student.tomasgray.capstone.ui.faculty.FacultyDetailsActivity.ARG_FACULTY_ID;

public class FacultyListAdapter extends RecyclerView.Adapter<FacultyListAdapter.FacultyViewHolder>
{
    private static final String LOG_TAG = "FacultyAdapter";


    private List<Faculty> faculty;
    private final Context context;

    FacultyListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view
                = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.faculty_card, parent, false);
        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position)
    {
        // Get a faculty member from list
        Faculty member = faculty.get(position);

        if(member != null) {
            String name = member.getFirstName() + " " + member.getLastName();
            holder.facultyName.setText(name);
            holder.facultyPhone.setText(member.getPhoneFormatted());
            holder.facultyEmail.setText(member.getEmail());
            holder.facultyCard.setOnClickListener(v -> {
                Log.i(
                        LOG_TAG,
                        "Faculty card clicked for ID: " +
                                member.getFacultyId().toString()
                );

                // Start faculty details activity
                Intent intent = new Intent(context, FacultyDetailsActivity.class);
                intent.putExtra(ARG_FACULTY_ID, member.getFacultyId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if(faculty == null)
            return 0;
        else
            return faculty.size();
    }

    void setData(List<Faculty> faculty)
    {
        Log.i(LOG_TAG, "Setting data for Adapter: " + faculty);
        this.faculty = faculty;
        // Update GUI
        notifyDataSetChanged();
    }

    /**
     * ViewHolder nested class
     */
    public static class FacultyViewHolder extends RecyclerView.ViewHolder
    {
        final View facultyCard;
        final TextView facultyName;
        final TextView facultyPhone;
        final TextView facultyEmail;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Attach Views
            facultyCard = itemView;
            facultyName = itemView.findViewById(R.id.facultyName);
            facultyPhone = itemView.findViewById(R.id.facultyPhone);
            facultyEmail = itemView.findViewById(R.id.facultyEmail);
        }
    }
}
