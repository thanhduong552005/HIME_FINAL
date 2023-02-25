package com.example.hime_droid.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hime_droid.R;
import com.example.hime_droid.WebviewActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<ItemModel> localDataSet;
    public static Context ctx;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvShorttext;
//        private final Button btn;
        private final MaterialCardView cardView;
        public String html;

        public void setHtml(String newHtml) {
            html = newHtml;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
        public TextView getTvShortText() {
            return tvShorttext;
        }
//        public Button getButton() {
//            return btn;
//        }
        public MaterialCardView getCardView() {
            return cardView;
        }

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvTitle = (TextView) view.findViewById(R.id.title);
            tvShorttext = (TextView) view.findViewById(R.id.shorttext);
//            btn = (Button) view.findViewById(R.id.btn);
            cardView = (MaterialCardView) view.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webviewIntent = new Intent(ctx, WebviewActivity.class);
                    Log.d("onclick", html);
                    webviewIntent.putExtra("HTML", html);
                    ctx.startActivity(webviewIntent);
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet Suggestion[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ListAdapter(ArrayList<ItemModel> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view, viewGroup, false);

        ctx = viewGroup.getContext();

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTvTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getTvShortText().setText(localDataSet.get(position).getShorttext());
        viewHolder.setHtml(localDataSet.get(position).getHtml());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
