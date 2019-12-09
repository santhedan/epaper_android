package com.dandekar.epaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.Publication;
import com.dandekar.epaper.data.displaymodel.Page;
import com.dandekar.epaper.holder.PageNameHolder;
import com.dandekar.epaper.holder.ThumbnailHolder;
import com.dandekar.epaper.util.ApplicationCache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PageThumbnailAdapter extends RecyclerView.Adapter {

    public static final String PAGE = "Page:";
    private List<Page> pages;
    private View.OnClickListener listener;
    private View.OnLongClickListener longlistener;
    private static final String pageNameFormat = "%s (%d)";

    Map<String, String> itemsDisplayed;

    public PageThumbnailAdapter(List<Page> pages, View.OnClickListener listener, View.OnLongClickListener longlistener) {
        this.pages = pages;
        this.listener = listener;
        this.longlistener = longlistener;
        this.itemsDisplayed = new ConcurrentHashMap<>();
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
                if (ApplicationCache.publication == Publication.Mirror) {
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.mirror_page_thumbnail_cell, parent, false);
                    holder = new ThumbnailHolder(layout);
                } else {
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.page_thumbnail_cell, parent, false);
                    holder = new ThumbnailHolder(layout);
                }
            }
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PageNameHolder) {
            String existingTag = (String) ((PageNameHolder)holder).pageNameLayout.getTag();
            if (existingTag != null) {
                this.itemsDisplayed.remove(existingTag);
            }
            ((PageNameHolder)holder).pageName.setText(pages.get(position).getName());
            String newTag = PAGE + position;
            this.itemsDisplayed.put(newTag, newTag);
            ((PageNameHolder)holder).pageNameLayout.setTag(newTag);
            ((PageNameHolder)holder).pageNameLayout.setOnClickListener(listener);
        } else if (holder instanceof ThumbnailHolder) {
            String existingTag = (String) ((ThumbnailHolder)holder).pageThumbnail.getTag();
            if (existingTag != null) {
                this.itemsDisplayed.remove(existingTag);
            }
            ((ThumbnailHolder)holder).pageThumbnail.setImageBitmap(pages.get(position).getThumbnail());
            String newTag = PAGE + position;
            this.itemsDisplayed.put(newTag, newTag);
            ((ThumbnailHolder)holder).pageThumbnail.setTag(newTag);
            String pageName = String.format(pageNameFormat, pages.get(position).getName(), position+1);
            ((ThumbnailHolder)holder).pageName.setText(pageName);
            ((ThumbnailHolder)holder).pageThumbnail.setOnClickListener(listener);
            ((ThumbnailHolder)holder).pageThumbnail.setOnLongClickListener(longlistener);
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

    public void cleanup() {
        if (pages != null) {
            for (Page p: pages) {
                p.cleanup();
            }
            pages.clear();
        }
    }
}
