package com.dandekar.epaper.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;

public class ThumbnailHolder extends RecyclerView.ViewHolder {

    public ImageView pageThumbnail;
    public TextView pageName;

    public ThumbnailHolder(@NonNull View itemView) {
        super(itemView);
        this.pageThumbnail = itemView.findViewById(R.id.thumbnail);
        this.pageName = itemView.findViewById(R.id.pageName);
    }
}
