package com.dandekar.epaper.divider;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;

public class GrayVerticalDividerDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;

    public GrayVerticalDividerDecoration(Resources resources) {
        divider = resources.getDrawable(R.drawable.divider_vertical);
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int right = left + divider.getIntrinsicWidth();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}