package com.dandekar.epaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.displaymodel.Page;
import com.dandekar.epaper.holder.PageNameHolder;
import com.dandekar.epaper.holder.ThumbnailHolder;

import java.util.List;

public class PageThumbnailAdapter extends RecyclerView.Adapter {

    public static final String PAGE = "Page:";
    private List<Page> pages;
    private View.OnClickListener listener;
    private static final String pageNameFormat = "%s (%d)";

    public PageThumbnailAdapter(List<Page> pages, View.OnClickListener listener) {
        this.pages = pages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 1: {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.page_textname_cell, parent, false);
                holder = new PageNameHolder(layout);
            }
                break;
            case 2: {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.page_thumbnail_cell, parent, false);
                holder = new ThumbnailHolder(layout);
            }
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PageNameHolder) {
            ((PageNameHolder)holder).pageName.setText(pages.get(position).getName());
            ((PageNameHolder)holder).pageNameLayout.setTag(PAGE + position);
            ((PageNameHolder)holder).pageNameLayout.setOnClickListener(listener);
        } else if (holder instanceof ThumbnailHolder) {
            ((ThumbnailHolder)holder).pageThumbnail.setImageBitmap(pages.get(position).getThumbnail());
            ((ThumbnailHolder)holder).pageThumbnail.setTag(PAGE + position);
            String pageName = String.format(pageNameFormat, pages.get(position).getName(), position+1);
            ((ThumbnailHolder)holder).pageName.setText(pageName);
            ((ThumbnailHolder)holder).pageThumbnail.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (pages.get(position).getThumbnail() == null)? 1: 2;
    }

    @Override
    public int getItemCount() {
        if (pages != null) {
            return  pages.size();
        }
        return 0;
    }
}
