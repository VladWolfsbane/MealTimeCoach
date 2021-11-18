package com.application.mealtimecoach.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.mealtimecoach.R;
import com.application.mealtimecoach.models.Recipe;
import com.astritveliu.boom.Boom;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.MyViewHolder> {

    private RequestManager glideRequestManager;

    private ArrayList<Recipe> mAdapterArrayList = new ArrayList<>();
    private RecipesRecyclerViewAdapter.onItemCLickListener mListener;

    public void setArraylist(ArrayList<Recipe> exerciseArrayList) {
        this.mAdapterArrayList = exerciseArrayList;
        notifyItemRangeInserted(0, exerciseArrayList.size());

    }

    public void setupGlide(RequestManager requestManager) {
        this.glideRequestManager = requestManager;
    }

    public interface onItemCLickListener {
        void onItemClick(int mPosition);
    }

    public void setOnItemClickListener(RecipesRecyclerViewAdapter.onItemCLickListener listener) {
        mListener = listener;
    }

    public ArrayList<Recipe> getExercisesArraylist() {
        return this.mAdapterArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v1 = inflater.inflate(R.layout.recipes_recycler_view_layout, parent,
                false);
        viewHolder = new MyViewHolder(v1, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        Recipe exercise = mAdapterArrayList.get(position);

        String text = (position + 1) + ". " + exercise.getTitle();
        viewHolder.titleTextview.setText(text);
        viewHolder.descriptionTextview.setText(exercise.getDescription());

        glideRequestManager
                .load(exercise.getImageUrl())
                .centerCrop()
                .into(viewHolder.exerciseImageView);

    }

    @Override
    public int getItemCount() {
        return mAdapterArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView exerciseImageView;
        TextView titleTextview;
        TextView descriptionTextview;

        public MyViewHolder(@NonNull View itemView, final RecipesRecyclerViewAdapter.onItemCLickListener listener) {
            super(itemView);
            exerciseImageView = itemView.findViewById(R.id.recipe_image_view);
            titleTextview = itemView.findViewById(R.id.title_text_view);
            descriptionTextview = itemView.findViewById(R.id.description_text_view);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });

            new Boom(itemView);
        }
    }
}
