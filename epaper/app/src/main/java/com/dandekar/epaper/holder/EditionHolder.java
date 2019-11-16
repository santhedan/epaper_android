package com.dandekar.epaper.holder;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditionHolder extends RecyclerView.ViewHolder {

    public ImageView editionImageView;

    public EditionHolder(@NonNull ImageView itemView) {
        super(itemView);
        this.editionImageView = itemView;
    }
}
