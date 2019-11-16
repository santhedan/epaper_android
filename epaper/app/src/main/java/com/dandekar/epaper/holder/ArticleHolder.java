package com.dandekar.epaper.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;

public class ArticleHolder extends RecyclerView.ViewHolder {

    public TextView articleTitle;
    public TextView articleMeta;
    public TextView articleSnippet;

    public ArticleHolder(@NonNull View itemView) {
        super(itemView);
        articleTitle = itemView.findViewById(R.id.articleTitle);
        articleMeta = itemView.findViewById(R.id.articleMeta);
        articleSnippet = itemView.findViewById(R.id.articleSnippet);
    }
}
