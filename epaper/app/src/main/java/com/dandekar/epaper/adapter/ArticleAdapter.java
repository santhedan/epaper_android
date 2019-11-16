package com.dandekar.epaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.displaymodel.Article;
import com.dandekar.epaper.holder.ArticleHolder;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter {

    private List<Article> articles;
    private View.OnClickListener listener;

    public ArticleAdapter(List<Article> articles, View.OnClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_cell, parent, false);
        ArticleHolder holder = new ArticleHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemView.setTag("Article@"+ article.getArticleURL() + "@" + article.getId());
        holder.itemView.setOnClickListener(listener);
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleHolder.articleTitle.setText(article.getTitle());
        articleHolder.articleMeta.setText(article.getMetaInfoForDisplay());
        articleHolder.articleSnippet.setText(article.getSnippet());
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        }
        return 0;
    }
}
