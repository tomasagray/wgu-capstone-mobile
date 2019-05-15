package edu.wgu.student.tomasgray.captstone.ui.views;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.wgu.student.tomasgray.captstone.data.model.Topic;

public class TopicButtonAdapter extends RecyclerView.Adapter<TopicButtonAdapter.ViewHolder>
{
    private List<Topic> data;

    public void setData(List<Topic> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Nested class for TopicButton ViewHolders
     */
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        final View view;

        ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
        }
    }
}
