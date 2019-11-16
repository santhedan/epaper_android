package com.dandekar.epaper.holder;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;

public class PageNameHolder extends RecyclerView.ViewHolder {

    public LinearLayout pageNameLayout;
    public TextView pageName;

    public PageNameHolder(LinearLayout pageName) {
        super(pageName);
        this.pageNameLayout = pageName;
        this.pageName = pageNameLayout.findViewById(R.id.pageName);
    }
}
