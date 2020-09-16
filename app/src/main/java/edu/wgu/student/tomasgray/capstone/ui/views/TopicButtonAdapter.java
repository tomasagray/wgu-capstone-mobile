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

package edu.wgu.student.tomasgray.capstone.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.wgu.student.tomasgray.capstone.R;
import edu.wgu.student.tomasgray.capstone.data.model.Topic;

public class TopicButtonAdapter extends RecyclerView.Adapter<TopicButtonAdapter.TopicViewHolder>
{
    private static final String LOG_TAG = "TopicButtonAdapt";


    private List<Topic> data;
    private RecyclerView recyclerView;
    private OnAdapterInteractionListener callback;

    public void setAdapterInteractionListener(OnAdapterInteractionListener callback) {
        this.callback = callback;
    }
    public interface OnAdapterInteractionListener {
        void onItemClick(int position);
    }

    public void setData(List<Topic> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view
                = LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.topic_card,
                            parent,
                            false
                    );
        // Attach click handler
        view.setOnClickListener(v -> {
            int itemPosition = recyclerView.getChildAdapterPosition(v);
            callback.onItemClick(itemPosition);
        });
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position)
    {
        // Get our Topic
        Topic topic = data.get(position);

        if(topic != null) {
            // Set GUI data
            holder.flagText.setText(topic.getHeaderFlag());
            holder.topicHeading.setText(topic.getTitle());
            holder.topicText.setText(topic.getTopicText());
        }
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * Nested class for TopicButton ViewHolders
     */
    static class TopicViewHolder extends RecyclerView.ViewHolder
    {
        final View view;
        TextView flagText;
        TextView topicHeading;
        TextView topicText;

        TopicViewHolder(@NonNull View view) {
            super(view);
            this.view = view.findViewById(R.id.cv);
            flagText = view.findViewById(R.id.flagText);
            topicHeading = view.findViewById(R.id.topicHeading);
            topicText = view.findViewById(R.id.topicText);
        }
    }
}
