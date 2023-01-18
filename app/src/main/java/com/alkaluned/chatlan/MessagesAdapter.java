package com.alkaluned.chatlan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<MessageModel> localDataSet;

    /**
     * Initialize the dataset of the Adapter
     * <p>
     * data containing the data to populate views to be used
     * by RecyclerView
     */
    public MessagesAdapter(List<MessageModel> data) {
        localDataSet = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        MessageModel data = localDataSet.get(position);

        viewHolder.getMessageRow().setText(data.getMessage());
        viewHolder.getTime().setText(data.getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageRow;
        private final TextView time;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            messageRow = (TextView) view.findViewById(R.id.messageString);
            time = (TextView) view.findViewById(R.id.time);
        }

        public TextView getMessageRow() {
            return messageRow;
        }

        public TextView getTime() {
            return time;
        }
    }
}

