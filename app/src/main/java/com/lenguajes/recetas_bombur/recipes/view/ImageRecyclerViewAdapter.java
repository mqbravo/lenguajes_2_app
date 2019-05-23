package com.lenguajes.recetas_bombur.recipes.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>{

    private ArrayList<String> mPaths;
    private int cardLayout;
    private int imageViewID;
    private Activity activity;

    public ImageRecyclerViewAdapter(ArrayList<String> mPaths, int cardLayout, int imageViewID, Activity activity) {
        this.mPaths = mPaths;
        this.cardLayout = cardLayout;
        this.activity = activity;
        this.imageViewID = imageViewID;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Gets the view from the XML layout
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(cardLayout, viewGroup, false);

        //Pass the view with the card cardLayout
        return new ImageRecyclerViewAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        final String path = mPaths.get(i);


        Glide.with(activity).load(path).into(imageViewHolder.imageCard);

    }

    @Override
    public int getItemCount() {
        return mPaths.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageCard;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCard = itemView.findViewById(imageViewID);
        }
    }

}
